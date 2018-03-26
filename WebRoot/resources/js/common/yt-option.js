/**
 * base_path : 接口访问路径
 * 
 * websit_path : 前端服务路径
 * 
 * is_test:正式开发时改成false
 * 
 * menu_data:一级菜单 数据
 * 
 * 
 * menu_data_two:二级菜单数据
 * 
 */

var $yt_option={
	/*node 访问路径*/
	base_path:'http://127.0.0.1/blockchain/',
	websit_path:'http://127.0.0.1/blockchain/',
	is_test:true,
	menu_data:[
		{
			pkId:1,
			menuName:"代码管理"
		},{
			pkId:2,
			menuName:"项目管理"
		}
	],
	menu_data_two:[
		{
			pkId:3,
			menuName:"代码生成",
			menuUrl:"ggdo/toListTables.action",
			parentId:1
		}
	]
}