package com.sikiedu.betobe.service.impl;

import com.sikiedu.betobe.domain.Barrage;
import com.sikiedu.betobe.repository.BarrageRepository;
import com.sikiedu.betobe.service.BarrageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhu
 * @date 2021/5/26 15:37:15
 * @description
 */
@Service
public class BarrageServiceImpl implements BarrageService {

	@Autowired
	private BarrageRepository barrageRepository;

	@Override
	public void saveBarrage(Barrage barrage) {
		barrageRepository.save(barrage);
	}
}
