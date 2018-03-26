<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.2//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${MAPPER_PACKAGE_NAME}">

	<!-- 查询全部数据 -->
	<select id="lookForAll" resultType="${BEAN_PACKAGE_NAME}">
		${SQL_LOOK_FOR_ALL}
	</select>
	
	<!-- 根据ID查询数据 -->
	<select id="getBeanById" resultType="${BEAN_PACKAGE_NAME}">
		${SQL_GET_BEAN_BY_ID}
	</select>
	
	<!-- 按条件分页查询数据 -->
	<select id="lookForAllByList" resultType="${BEAN_PACKAGE_NAME}" parameterType="hashmap">
		${SQL_LOOK_FOR_ALL_BY_LIST}
	</select>
	
	<!-- 按条件查询数据总数 -->
	<select id="lookForAllByCount" resultType="int" parameterType="hashmap">
		${SQL_LOOK_FOR_ALL_BY_COUNT}
	</select>


	<!-- 增加新数据 -->
	<insert id="saveBean" parameterType="${BEAN_PACKAGE_NAME}">
		<!--新增后返回id-->
		${SQL_SAVE_BEAN}
	</insert>

	<!-- 批量增加新数据-->
	<insert id="saveAllBean" parameterType="list">
		<!--新增后返回id-->
		${SQL_SAVE_ALL_BEAN}
	</insert>


	<!-- 修改Bean -->
	<update id="updateBean"  parameterType="${BEAN_PACKAGE_NAME}">
		${SQL_UPDATE_BEAN}
	</update>

	<!-- 根据id删除Bean -->
	<delete id="deleteBeanById" parameterType="long">
		${SQL_DELETE_BEAN_BY_ID}
	</delete>
	
	<!-- 根据一批id删除一批Bean -->
	<delete id="deleteAllBean" parameterType = "list">
		${SQL_DELETE_ALL_BEAN}
	</delete>

	<!-- 批量更新Bean -->
	<update id="updateBatch"  parameterType="list">  
	    ${SQL_UPDATE_ALL_BEAN}  
	</update>

</mapper>
