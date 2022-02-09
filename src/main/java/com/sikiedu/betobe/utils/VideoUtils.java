package com.sikiedu.betobe.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.*;
/**
 * @author zhu
 * @date 2021/5/5 22:27:15
 * @description 获取视频长度
 */
public class VideoUtils {

	// 在腾讯云中删除视频
	public static void deleteVideoOnTencent(String fileId) {
		try{

			Credential cred = new Credential("AKIDjEVO5Xk6No0xMWJ6OSgeNuIYhdOIrneH", "ZYnomVIqAIf3Xv60ue8OyVownPsGRFJC");

			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("vod.tencentcloudapi.com");

			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);

			VodClient client = new VodClient(cred, "", clientProfile);

			DeleteMediaRequest req = new DeleteMediaRequest();
			req.setFileId(fileId);

			DeleteMediaResponse resp = client.DeleteMedia(req);

			System.out.println(DeleteMediaResponse.toJsonString(resp));
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}
	}

	// 在腾讯云中获取视频秒数
	public static Integer getVideoSeconds(String vid) {
		int target = 0;

		try{

			Credential cred = new Credential("AKIDjEVO5Xk6No0xMWJ6OSgeNuIYhdOIrneH", "ZYnomVIqAIf3Xv60ue8OyVownPsGRFJC");

			HttpProfile httpProfile = new HttpProfile();
			httpProfile.setEndpoint("vod.tencentcloudapi.com");

			ClientProfile clientProfile = new ClientProfile();
			clientProfile.setHttpProfile(httpProfile);

			VodClient client = new VodClient(cred, "", clientProfile);

			DescribeMediaInfosRequest req = new DescribeMediaInfosRequest();
			String[] fileIds1 = {vid};
			req.setFileIds(fileIds1);


			DescribeMediaInfosResponse resp = client.DescribeMediaInfos(req);

			for (MediaInfo mediaInfo : resp.getMediaInfoSet()) {
				target = mediaInfo.getMetaData().getDuration().intValue();
			}

			//System.out.println(DescribeMediaInfosResponse.toJsonString(resp));
		} catch (TencentCloudSDKException e) {
			System.out.println(e.toString());
		}

		return target;
	}



}
