/**
 * 此文件为模态框显示与隐藏文件
 * 
 */
// 添加用户
$(".adduser").on("click", function() {
    // 模态框显隐，必须
    $(".modal-bg").removeClass("dp-n");
    $(".modal-conbox").addClass("showm");
    $(".modaldet").addClass("dp-n");
    $(".form-horizontal").removeClass("dp-n");
    $(".modaltitle").html("添加用户");
});
//关闭模态框
$(".closemodal").on("click", function() {
    // 模态框显隐，必须
    $(".modal-bg").addClass("dp-n");
});
// 编辑方法
$(".editmsg").on("click", function() {
    // 模态框显隐，必须
    $(".modal-bg").removeClass("dp-n");
    $(".modal-conbox").addClass("showm");
    $(".modaldet").addClass("dp-n");
    $(".form-horizontal").removeClass("dp-n");
    $(".modaltitle").html("编辑信息");
    // 下面为处理方法
});
// 删除方法
$(".deletelist").on("click", function() {
    // 模态框显隐，必须
    $(".modal-bg").removeClass("dp-n");
    $(".modal-conbox").addClass("showm");
    $(".modaldet").removeClass("dp-n");
    $(".form-horizontal").addClass("dp-n");
    $(".modaltitle").html("信息");
});