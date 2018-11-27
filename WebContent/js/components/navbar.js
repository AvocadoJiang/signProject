var NavBar = (function() {

  var usrinfo = JSON.parse(localStorage.getItem('uinfo'));
  var usrType = usrinfo.userSysType.toString();

  var barTpl = {
    0: '\
    <div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>\
      <ul>\
        <li :class="{active: active === 2}"><a href="noticeMgr.html"><i class="icon icon-bell"></i> <span>通知</span></a> </li>\
        <li :class="{active: active === 1}"><a href="myinfo.html"><i class="icon icon-user"></i> <span>个人信息</span></a> </li>\
        <li :class="{active: active === 3}"><a href="mybills.html"><i class="icon icon-money"></i> <span>缴费记录</span></a> </li>\
        <li :class="{active: active === 4}"><a href="settings.html"><i class="icon icon-cogs"></i> <span>个人设置</span></a> </li>\
      </ul>\
    </div>',
    1: '\
    <div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>\
      <ul>\
        <li :class="{active: active === 2}"><a href="noticeMgr.html"><i class="icon icon-bell"></i> <span>通知</span></a> </li>\
        <li :class="{active: active === 1}"><a href="myinfo.html"><i class="icon icon-user"></i> <span>个人信息</span></a> </li>\
        <li :class="{active: active === 3}"><a href="mybills.html"><i class="icon icon-money"></i> <span>缴费记录</span></a> </li>\
        <li :class="{active: active === 4}"><a href="settings.html"><i class="icon icon-cogs"></i> <span>个人设置</span></a> </li>\
      </ul>\
    </div>',
    2: '\
    <div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>\
      <ul>\
      <li :class="{active: active === 2}"><a href="noticeMgr.html"><i class="icon icon-bell"></i> <span>通知</span></a> </li>\
      <li :class="{active: active === 1}"><a href="myinfo.html"><i class="icon icon-user"></i> <span>个人信息</span></a> </li>\
      <li :class="{active: active === 4}"><a href="settings.html"><i class="icon icon-cogs"></i> <span>个人设置</span></a> </li>\
      </ul>\
    </div>',
    3: '\
    <div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>\
      <ul>\
        <li :class="{active: active === 0}"><a href="home.html"><i class="icon icon-table"></i> <span>综合信息管理</span></a> </li>\
        <li :class="{active: active === 1}" class="submenu"> <a href="#"><i class="icon icon-user-md"></i> <span>用户</span> <span class="label label-important" style="background-color: transparent"><i class="icon icon-caret-down"></i></span></a>\
          <ul>\
            <li><a href="addUsr.html">添加用户</a></li>\
            <li><a href="usrMgr.html">用户管理</a></li>\
          </ul>\
        </li>\
        <li :class="{active: active === 2}" class="submenu"> <a href="#"><i class="icon icon-bell"></i> <span>通知</span> <span class="label label-important" style="background-color: transparent"><i class="icon icon-caret-down"></i></span></a>\
          <ul>\
            <li><a href="notice.html">添加通知</a></li>\
            <li><a href="noticeMgr.html">通知管理</a></li>\
          </ul>\
        </li>\
        <li :class="{active: active === 3}"><a href="bills.html"><i class="icon icon-money"></i> <span>账单管理</span></a> </li>\
        <li :class="{active: active === 4}"><a href="settings.html"><i class="icon icon-cogs"></i> <span>个人设置</span></a> </li>\
      </ul>\
    </div>'
  }
  
  var NavBar = {
    template: barTpl[usrType],
    props: {
      active: {
        default: 0
      }
    }
  };
  
  return NavBar;
})();
