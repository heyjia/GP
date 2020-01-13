前端采用 bootstrap thymleaf
中间件使用redis
后端使用 springboot mybatis mysql
安全框架 shiro
功能：shiro做权限控制，不同角色权限不同，显示不同页面
      包含用户管理 角色管理 权限管理 部门管理 岗位岗位
      用redis存储用户的信息 以及登录的ip 限制同个用户在不同地点的访问（使用拦截器）
设计模式：
     模板模式 接口-抽象类-实现类
    
