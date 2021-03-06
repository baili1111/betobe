'use strict';

!function($) {

/**
 * Tooltip module.
 * @module foundation.tooltip
 * @requires foundation.util.box
 * @requires foundation.util.triggers
 */

class Tooltip {
  /**
   * Creates a new instance of a Tooltip.
   * @class
   * @fires Tooltip#init
   * @param {jQuery} element - jQuery object to attach a tooltip to.
   * @param {Object} options - object to extend the default configuration.
   */
  constructor(element, options) {
    this.$element = element;
    this.options = $.extend({}, Tooltip.defaults, this.$element.data(), options);

    this.isActive = false;
    this.isClick = false;
    this._init();

    Foundation.registerPlugin(this, 'Tooltip');
  }

  /**
   * Initializes the tooltip by setting the creating the tip element, adding it's text, setting private variables and setting attributes on the anchor.
   * @private
   */
  _init() {
    var elemId = this.$element.attr('aria-describedby') || Foundation.GetYoDigits(6, 'tooltip');

    this.options.positionClass = this._getPositionClass(this.$element);
    this.options.tipText = this.options.tipText || this.$element.attr('title');
    this.template = this.options.template ? $(this.options.template) : this._buildTemplate(elemId);

    this.template.appendTo(document.body)
        .text(this.options.tipText)
        .hide();

    this.$element.attr({
      'title': '',
      'aria-describedby': elemId,
      'data-yeti-box': elemId,
      'data-toggle': elemId,
      'data-resize': elemId
    }).addClass(this.triggerClass);

    //helper variables to track movement on collisions
    this.usedPositions = [];
    this.counter = 4;
    this.classChanged = false;

    this._events();
  }

  /**
   * Grabs the current positioning class, if present, and returns the value or an empty string.
   * @private
   */
  _getPositionClass(element) {
    if (!element) { return ''; }
    // var position = element.attr('class').match(/top|left|right/g);
    var position = element[0].className.match(/\b(top|left|right)\b/g);
        position = position ? position[0] : '';
    return position;
  };
  /**
   * builds the tooltip element, adds attributes, and returns the templates.
   * @private
   */
  _buildTemplate(id) {
    var templateClasses = (`${this.options.tooltipClass} ${this.options.positionClass} ${this.options.templateClasses}`).trim();
    var $template =  $('<div></div>').addClass(templateClasses).attr({
      'role': 'tooltip',
      'aria-hidden': true,
      'data-is-active': false,
      'data-is-focus': false,
      'id': id
    });
    return $template;
  }

  /**
   * Function that gets called if a collision event is detected.
   * @param {String} position - positioning class to try
   * @private
   */
  _reposition(position) {
    this.usedPositions.push(position ? position : 'bottom');

    //default, try switching to opposite side
    if (!position && (this.usedPositions.indexOf('top') < 0)) {
      this.template.addClass('top');
    } else if (position === 'top' && (this.usedPositions.indexOf('bottom') < 0)) {
      this.template.removeClass(position);
    } else if (position === 'left' && (this.usedPositions.indexOf('right') < 0)) {
      this.template.removeClass(position)
          .addClass('right');
    } else if (position === 'right' && (this.usedPositions.indexOf('left') < 0)) {
      this.template.removeClass(position)
          .addClass('left');
    }

    //if default change didn't work, try bottom or left first
    else if (!position && (this.usedPositions.indexOf('top') > -1) && (this.usedPositions.indexOf('left') < 0)) {
      this.template.addClass('left');
    } else if (position === 'top' && (this.usedPositions.indexOf('bottom') > -1) && (this.usedPositions.indexOf('left') < 0)) {
      this.template.removeClass(position)
          .addClass('left');
    } else if (position === 'left' && (this.usedPositions.indexOf('right') > -1) && (this.usedPositions.indexOf('bottom') < 0)) {
      this.template.removeClass(position);
    } else if (position === 'right' && (this.usedPositions.indexOf('left') > -1) && (this.usedPositions.indexOf('bottom') < 0)) {
      this.template.removeClass(position);
    }
    //if nothing cleared, set to bottom
    else {
      this.template.removeClass(position);
    }
    this.classChanged = true;
    this.counter--;
  }

  /**
   * sets the position class of an element and recursively calls itself until there are no more possible positions to attempt, or the tooltip element is no longer colliding.
   * if the tooltip is larger than the screen width, default to full width - any user selected margin
   * @private
   */
  _setPosition() {
    var position = this._getPositionClass(this.template),
        $tipDims = Foundation.Box.GetDimensions(this.template),
        $anchorDims = Foundation.Box.GetDimensions(this.$element),
        direction = (position === 'left' ? 'left' : ((position === 'right') ? 'left' : 'top')),
        param = (direction === 'top') ? 'height' : 'width',
        offset = (param === 'height') ? this.options.vOffset : this.options.hOffset,
        _this = this;

    if (($tipDims.width >= $tipDims.windowDims.width) || (!this.counter && !Foundation.Box.ImNotTouchingYou(this.template))) {
      this.template.offset(Foundation.Box.GetOffsets(this.template, this.$element, 'center bottom', this.options.vOffset, this.options.hOffset, true)).css({
      // this.$element.offset(Foundation.GetOffsets(this.templates, this.$element, 'center bottom', this.options.vOffset, this.options.hOffset, true)).css({
        'width': $anchorDims.windowDims.width - (this.options.hOffset * 2),
        'height': 'auto'
      });
      return false;
    }

    this.template.offset(Foundation.Box.GetOffsets(this.template, this.$element,'center ' + (position || 'bottom'), this.options.vOffset, this.options.hOffset));

    while(!Foundation.Box.ImNotTouchingYou(this.template) && this.counter) {
      this._reposition(position);
      this._setPosition();
    }
  }

  /**
   * reveals the tooltip, and fires an event to close any other open tooltips on the page
   * @fires Tooltip#closeme
   * @fires Tooltip#show
   * @function
   */
  show() {
    if (this.options.showOn !== 'all' && !Foundation.MediaQuery.atLeast(this.options.showOn)) {
      // console.error('The screen is too small to display this tooltip');
      return false;
    }

    var _this = this;
    this.template.css('visibility', 'hidden').show();
    this._setPosition();

    /**
     * Fires to close all other open tooltips on the page
     * @event Closeme#tooltip
     */
    this.$element.trigger('closeme.zf.tooltip', this.template.attr('id'));


    this.template.attr({
      'data-is-active': true,
      'aria-hidden': false
    });
    _this.isActive = true;
    // console.log(this.templates);
    this.template.stop().hide().css('visibility', '').fadeIn(this.options.fadeInDuration, function() {
      //maybe do stuff?
    });
    /**
     * Fires when the tooltip is shown
     * @event Tooltip#show
     */
    this.$element.trigger('show.zf.tooltip');
  }

  /**
   * Hides the current tooltip, and resets the positioning class if it was changed due to collision
   * @fires Tooltip#hide
   * @function
   */
  hide() {
    // console.log('hiding', this.$element.data('yeti-box'));
    var _this = this;
    this.template.stop().attr({
      'aria-hidden': true,
      'data-is-active': false
    }).fadeOut(this.options.fadeOutDuration, function() {
      _this.isActive = false;
      _this.isClick = false;
      if (_this.classChanged) {
        _this.template
             .removeClass(_this._getPositionClass(_this.template))
             .addClass(_this.options.positionClass);

       _this.usedPositions = [];
       _this.counter = 4;
       _this.classChanged = false;
      }
    });
    /**
     * fires when the tooltip is hidden
     * @event Tooltip#hide
     */
    this.$element.trigger('hide.zf.tooltip');
  }

  /**
   * adds event listeners for the tooltip and its anchor
   * TODO combine some of the listeners like focus and mouseenter, etc.
   * @private
   */
  _events() {
    var _this = this;
    var $template = this.template;
    var isFocus = false;

    if (!this.options.disableHover) {

      this.$element
      .on('mouseenter.zf.tooltip', function(e) {
        if (!_this.isActive) {
          _this.timeout = setTimeout(function() {
            _this.show();
          }, _this.options.hoverDelay);
        }
      })
      .on('mouseleave.zf.tooltip', function(e) {
        clearTimeout(_this.timeout);
        if (!isFocus || (!_this.isClick && _this.options.clickOpen)) {
          _this.hide();
        }
      });
    }

    if (this.options.clickOpen) {
      this.$element.on('mousedown.zf.tooltip', function(e) {
        e.stopImmediatePropagation();
        if (_this.isClick) {
          _this.hide();
          // _this.isClick = false;
        } else {
          _this.isClick = true;
          if ((_this.options.disableHover || !_this.$element.attr('tabindex')) && !_this.isActive) {
            _this.show();
          }
        }
      });
    }

    if (!this.options.disableForTouch) {
      this.$element
      .on('tap.zf.tooltip touchend.zf.tooltip', function(e) {
        _this.isActive ? _this.hide() : _this.show();
      });
    }

    this.$element.on({
      // 'toggle.zf.trigger': this.toggle.bind(this),
      // 'close.zf.trigger': this.hide.bind(this)
      'close.zf.trigger': this.hide.bind(this)
    });

    this.$element
      .on('focus.zf.tooltip', function(e) {
        isFocus = true;
        // console.log(_this.isClick);
        if (_this.isClick) {
          return false;
        } else {
          // $(window)
          _this.show();
        }
      })

      .on('focusout.zf.tooltip', function(e) {
        isFocus = false;
        _this.isClick = false;
        _this.hide();
      })

      .on('resizeme.zf.trigger', function() {
        if (_this.isActive) {
          _this._setPosition();
        }
      });
  }

  /**
   * adds a toggle method, in addition to the static show() & hide() functions
   * @function
   */
  toggle() {
    if (this.isActive) {
      this.hide();
    } else {
      this.show();
    }
  }

  /**
   * Destroys an instance of tooltip, removes templates element from the view.
   * @function
   */
  destroy() {
    this.$element.attr('title', this.template.text())
                 .off('.zf.trigger .zf.tootip')
                //  .removeClass('has-tip')
                 .removeAttr('aria-describedby')
                 .removeAttr('data-yeti-box')
                 .removeAttr('data-toggle')
                 .removeAttr('data-resize');

    this.template.remove();

    Foundation.unregisterPlugin(this);
  }
}

Tooltip.defaults = {
  disableForTouch: false,
  /**
   * Time, in ms, before a tooltip should open on hover.
   * @option
   * @example 200
   */
  hoverDelay: 200,
  /**
   * Time, in ms, a tooltip should take to fade into view.
   * @option
   * @example 150
   */
  fadeInDuration: 150,
  /**
   * Time, in ms, a tooltip should take to fade out of view.
   * @option
   * @example 150
   */
  fadeOutDuration: 150,
  /**
   * Disables hover events from opening the tooltip if set to true
   * @option
   * @example false
   */
  disableHover: false,
  /**
   * Optional addtional classes to apply to the tooltip templates on init.
   * @option
   * @example 'my-cool-tip-class'
   */
  templateClasses: '',
  /**
   * Non-optional class added to tooltip templates. Foundation default is 'tooltip'.
   * @option
   * @example 'tooltip'
   */
  tooltipClass: 'tooltip',
  /**
   * Class applied to the tooltip anchor element.
   * @option
   * @example 'has-tip'
   */
  triggerClass: 'has-tip',
  /**
   * Minimum breakpoint size at which to open the tooltip.
   * @option
   * @example 'small'
   */
  showOn: 'small',
  /**
   * Custom templates to be used to generate markup for tooltip.
   * @option
   * @example '&lt;div class="tooltip"&gt;&lt;/div&gt;'
   */
  template: '',
  /**
   * Text displayed in the tooltip templates on open.
   * @option
   * @example 'Some cool space fact here.'
   */
  tipText: '',
  touchCloseText: 'Tap to close.',
  /**
   * Allows the tooltip to remain open if triggered with a click or touch event.
   * @option
   * @example true
   */
  clickOpen: true,
  /**
   * Additional positioning classes, set by the JS
   * @option
   * @example 'top'
   */
  positionClass: '',
  /**
   * Distance, in pixels, the templates should push away from the anchor on the Y axis.
   * @option
   * @example 10
   */
  vOffset: 10,
  /**
   * Distance, in pixels, the templates should push away from the anchor on the X axis, if aligned to a side.
   * @option
   * @example 12
   */
  hOffset: 12
};

/**
 * TODO utilize resize event trigger
 */

// Window exports
Foundation.plugin(Tooltip, 'Tooltip');

}(jQuery);
