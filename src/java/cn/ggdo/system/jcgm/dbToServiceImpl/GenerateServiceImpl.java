﻿package cn.ggdo.system.jcgm.dbToServiceImpl;public class GenerateServiceImpl {	/**	 * 生成方法	 */	public void generate() {		System.out.println("生成[ServiceImpl]......");		try {			Handle handle = new Handle();			handle.setNameUntil();			handle.writeGenerate();			System.out.println("生成[ServiceImpl]成功!");		} catch (Exception e) {			System.out.println("生成[ServiceImpl]失败!");		}	}}