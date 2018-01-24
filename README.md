规矩：
A)Service/DAO 层方法命名规约<br>
    1） 获取单个对象的方法用 get 做前缀。<br>
    2） 获取多个对象的方法用 list 做前缀。<br>
    3） 获取统计值的方法用 count 做前缀。<br>
    4） 插入的方法用 save<br>
    5） 删除的方法用 remove<br>
    6） 修改的方法用 update 做前缀。<br>
B) 领域模型命名规约<br>
    1） 数据对象：xxxDO，xxx 即为数据表名。<br>
    2） 数据传输对象：xxxDTO，xxx 为业务领域相关的名称。<br>
    3） 展示对象：xxxVO，xxx 一般为网页名称。<br>
    4） POJO 是 DO/DTO/BO/VO 的统称，禁止命名成 xxxPOJO。<br>

未完成任务：
1 t_permission_info parentIds,permission两个字段目前没用，最后确定没用删除
2 建立日志模块
3 修改管理员和菜单信息把update和save合并，参照角色信息
4 在用户中添加角色 在角色中添加用户