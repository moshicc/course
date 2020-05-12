<template>
  <div>
    <p>
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>
    <pagination ref="pagination" v-bind:list="list"></pagination>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>名称</th>
        <th>课程ID</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="chapter in chapters">
        <td>{{chapter.id}}</td>
        <td>{{chapter.name}}</td>
        <td>{{chapter.courseId}}</td>

        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(chapter)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>
            <button v-on:click="del(chapter.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>

          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="chapter.name" type="text" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程ID</label>
                <div class="col-sm-10">
                  <input v-model="chapter.courseId" type="text" class="form-control" placeholder="课程id">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
    import Pagination from "../../components/pagination"
    export default {
        components:{Pagination},
        name: "chapter",
        data: function(){
            return {
                chapter: {},
                chapters: []
            }
        },
        mounted:function(){
            let  _this = this;
            _this.$refs.pagination.size=5;
            _this.list(1);
            //sidebar激活样式 方法一
            //this.$parent.activeSidebar("business-chapter-sidebar");
        },
        methods:{
            /**
             * 点击【新增】
             */
            add(){
                let _this = this;
                _this.chapter ={};
                $("#form-modal").modal("show")
            },
            /**
             *点击【编辑】
             */
            edit(chapter){
                let _this = this;
                _this.chapter =$.extend({},chapter);
                $("#form-modal").modal("show")
            },
            /**
             * 列表查询
             * @param page
             */
            list(page) {
                let _this =this;
                Loading.show();
                _this.$ajax.post("http://127.0.0.1:9000/business/admin/chapter/list",{
                    page:page,
                    size:_this.$refs.pagination.size,
                }).then((resopnes)=>{
                    Loading.hide();
                    //response.data 获得的就是后端统一传来的responseDto，里面有success，code，message，content
                    let resp = resopnes.data;
                    //resp.content 就是封装的pageDto ，里面有页码num，页数size，内容list 等
                    _this.chapters = resp.content.list;
                    _this.$refs.pagination.render(page,resp.content.total);
                })
            },
            /**
             * 点击【保存】
             * @param page
             */
            save(page) {
                let _this =this;
                //保存校验
                if(!Validator.require(_this.chapter.name,"名称")
                    ||!Validator.require(_this.chapter.courseId,"课程ID")
                    ||!Validator.length(_this.chapter.courseId,"课程ID",1,8 )){
                    return;
                }

                Loading.show();
                _this.$ajax.post("http://127.0.0.1:9000/business/admin/chapter/save", _this.chapter).then((resopnes)=>{
                    Loading.hide();
                    let resp = resopnes.data;
                    if(resp.success){
                        //保存成功，关闭modal，并且刷新list
                        $("#form-modal").modal("hide");
                        _this.list(1);
                        Toast.success("保存成功！");
                    }else {
                        Toast.warning(resp.message);
                    }

                })
            },
            /**
             * 点击【删除】
             * @param id
             */
            del(id) {
                let _this =this;
                //sweetalert弹出框
                Confirm.show("删除大章后不可恢复，确认删除？",function () {
                    Loading.show();
                    _this.$ajax.delete("http://127.0.0.1:9000/business/admin/chapter/delete/"+id).then((resopnes)=>{
                        Loading.hide();
                        let resp = resopnes.data;
                        if(resp.success){
                            //删除成功，并且刷新list
                            _this.list(1);
                            Toast.success("删除成功！");
                        }
                    })
                });

            }
        }
    }
</script>