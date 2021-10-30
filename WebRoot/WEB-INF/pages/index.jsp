<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
  <head>
    <meta charset="UTF-8">
	<%@include file="/WEB-INF/pages/common/head_layui.jsp" %>
  </head>
  
  <body class="load-body">
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo"><a href="index.html">L-admin v2.0</a></div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe699;</i>
        </div>
        <!-- <ul class="layui-nav left fast-add" lay-filter="">
          <li class="layui-nav-item">
            <a href="javascript:;">+新增</a>
            <dl class="layui-nav-child"> 二级菜单
              <dd><a onClick="x_admin_show('资讯','http://www.baidu.com')"><i class="iconfont">&#xe6a2;</i>资讯</a></dd>
              <dd><a onClick="x_admin_show('图片','http://www.baidu.com')"><i class="iconfont">&#xe6a8;</i>图片</a></dd>
               <dd><a onClick="x_admin_show('用户','http://www.baidu.com')"><i class="iconfont">&#xe6b8;</i>用户</a></dd>
            </dl>
          </li>
        </ul> -->
        <ul class="layui-nav right" lay-filter="">
          <li class="layui-nav-item">
            <a href="javascript:;">${user.userName}</a>
            <dl class="layui-nav-child"> <!-- 二级菜单 -->
           <!--    <dd><a onClick="x_admin_show('个人信息','http://www.baidu.com')">个人信息</a></dd>
              <dd><a onClick="x_admin_show('切换帐号','http://www.baidu.com')">切换帐号</a></dd> -->
              <dd><a href="/admin/logout.do">退出</a></dd>
            </dl>
          </li>
          <li class="layui-nav-item to-index"><a href="#">前台首页</a></li>
        </ul>
        
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
     <!-- 左侧菜单开始 -->
    <div class="left-nav">
      <div id="side-nav">
        <ul id="nav">
             <li >
                <a href="javascript:;">
                    <i class="iconfont">&#xe6eb;</i>
                    <cite>主页</cite>
                    <i class="iconfont nav_right">&#xe6a7;</i>
                </a>
                <ul class="sub-menu">
                    <li><a _href="/admin/center/welcome.do"><i class="iconfont">&#xe6a7;</i><cite>控制台</cite></a></li >
                </ul>
            </li>
            <!--  <li >
                <a href="javascript:;">
                    <i class="iconfont">&#xe6e4;</i>
                    <cite>基本元素</cite>
                    <i class="iconfont nav_right">&#xe6a7;</i>
                </a>
                <ul class="sub-menu">
                    <li><a _href="html/unicode.html"><i class="iconfont">&#xe6a7;</i><cite>图标字体</cite></a></li>
                    <li><a _href="html/form1.html"><i class="iconfont">&#xe6a7;</i><cite>表单元素</cite></a></li>
                    <li> <a _href="html/form2.html"><i class="iconfont">&#xe6a7;</i><cite>表单组合</cite></a></li>
                    <li><a _href="html/buttons.html"><i class="iconfont">&#xe6a7;</i><cite>按钮</cite></a></li>
                    <li><a _href="html/nav.html"><i class="iconfont">&#xe6a7;</i><cite>导航/面包屑</cite></a></li>
                    <li><a _href="html/tab.html"><i class="iconfont">&#xe6a7;</i><cite>选项卡</cite></a></li>
                    <li><a _href="html/progress-bar.html"><i class="iconfont">&#xe6a7;</i><cite>进度条</cite></a></li>
                    <li><a _href="html/panel.html"><i class="iconfont">&#xe6a7;</i><cite>面板</cite></a></li>
                    <li><a _href="html/badge.html"><i class="iconfont">&#xe6a7;</i><cite>微章</cite></a></li>
                    <li><a _href="html/timeline.html"><i class="iconfont">&#xe6a7;</i><cite>时间线</cite></a></li>
                    <li><a _href="html/table-element.html"><i class="iconfont">&#xe6a7;</i><cite>静态表格</cite></a></li>
                    <li><a _href="html/anim.html"><i class="iconfont">&#xe6a7;</i><cite>动画</cite></a></li>
                </ul>
            </li> -->
            
            
            <li>
                <a href="javascript:;"><i class="iconfont">&#xe735;</i><cite>绕线画管理</cite><i class="iconfont nav_right">&#xe6a7;</i></a>
                <ul class="sub-menu">
                   <!--  <li><a _href="html/upload.html"><i class="iconfont">&#xe6a7;</i><cite>文件上传</cite></a></li>
                    <li><a _href="html/page.html"><i class="iconfont">&#xe6a7;</i><cite>分页</cite></a></li> -->
                    <li><a _href="/admin/center/package/list/ui.do"><i class="iconfont">&#xe6a7;</i><cite>模板列表</cite></a></li>
                    <li><a _href="/admin/center/user/list/ui.do"><i class="iconfont">&#xe6a7;</i><cite>用户列表</cite></a></li>
                    <li><a _href="/admin/center/secret/list/ui.do"><i class="iconfont">&#xe6a7;</i><cite>秘钥列表</cite></a></li>
                   <li><a _href="/admin/center/while/list/ui.do"><i class="iconfont">&#xe6a7;</i><cite>白名单列表</cite></a></li>
                   <li><a _href="/admin/center/category/list/ui.do"><i class="iconfont">&#xe6a7;</i><cite>类别列表</cite></a></li>
                   <!--  <li><a _href="html/carousel.html"><i class="iconfont">&#xe6a7;</i><cite>轮播图</cite></a></li>
                    <li><a _href="html/city.html"><i class="iconfont">&#xe6a7;</i><cite>城市三级联动</cite></a></li> -->
                </ul>
            </li>
             <!-- <li >
                <a href="javascript:;">
                    <i class="iconfont">&#xe6b4;</i>
                    <cite>排版布局</cite>
                    <i class="iconfont nav_right">&#xe6a7;</i>
                </a>
                <ul class="sub-menu">
                    <li><a _href="html/grid.html"><i class="iconfont">&#xe6a7;</i><cite>栅格</cite></a></li>
                    <li><a _href="html/welcome2.html"><i class="iconfont">&#xe6a7;</i><cite>排版</cite></a></li>
                </ul>
            </li> -->
                   
           <!--  <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe69e;</i>
                    <cite>订单管理</cite>
                    <i class="iconfont nav_right">&#xe6a7;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="html/order-list.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>订单列表</cite>
                        </a>
                    </li >
                </ul>
            </li> -->
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe726;</i>
                    <cite>管理员管理</cite>
                    <i class="iconfont nav_right">&#xe6a7;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="/admin/center/account/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>管理员列表</cite>
                        </a>
                    </li > 
                   <!--  <li>
                        <a _href="/admin/center/role/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>角色列表</cite>
                        </a>
                    </li >  -->
                 <!--    <li>
                        <a _href="html/admin-role.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>角色管理</cite>
                        </a>
                    </li > -->
                   
                   <!--  <li>
                        <a _href="/admin/center/system/category/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>权限分类</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="/admin/center/system/right/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>权限管理</cite>
                        </a>
                    </li > -->
                </ul>
            </li>            
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6f6;</i>
                    <cite>钉子画管理</cite>
                    <i class="iconfont nav_right">&#xe6a7;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="/admin/center/nailconfig/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>类型配置</cite>
                            
                        </a>
                    </li >
                    <li>
                        <a _href="/admin/center/naildetailconfig/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>颜色配置</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailpictureframe/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>画框配置</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailimagesize/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图片尺寸配置</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailorder/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>订单列表</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailWeightStock/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图钉库存</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailWeightStockHistory/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图钉库存记录</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailDrawingStock/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图纸库存</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailDrawingStockHistory/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图纸库存记录</cite>
                            
                        </a>
                    </li>
                    
                     <li>
                        <a _href="/admin/center/nailPictureframeStock/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>画框库存</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailPictureframeStockHistory/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>画框库存记录</cite>
                            
                        </a>
                    </li>
                   <!--  <li>
                        <a _href="/admin/center/nailWeightStock/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>秘钥列表</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a _href="/admin/center/nailwhile/list/ui.do">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>白名单列表</cite>
                            
                        </a>
                    </li> -->
                    
                </ul>
            </li>    
            
            
            
                   

<!-- <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6ae;</i>
                    <cite>系统统计</cite>
                    <i class="iconfont nav_right">&#xe6a7;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="html/echarts1.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>拆线图</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="html/echarts2.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>柱状图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="html/echarts3.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>地图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="html/echarts4.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>饼图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="html/echarts5.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>雷达图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="html/echarts6.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>k线图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="html/echarts7.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>热力图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="html/echarts8.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>仪表图</cite>
                        </a>
                    </li>
                </ul>
            </li>   -->          
         
         </ul>
      </div>
    </div>
    <!-- <div class="x-slide_left"></div> -->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
          <ul class="layui-tab-title">
            <li class="home"><i class="layui-icon">&#xe68e;</i>我的桌面</li>
          </ul>
          <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='/admin/center/welcome.do' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
          </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 中部结束 -->
    <!-- 底部开始 -->
    <!--<div class="footer">
        <div class="copyright">Copyright ©2019 L-admin v2.3 All Rights Reserved</div>  
    </div>-->
    <!-- 底部结束 -->
</body>
</html>
