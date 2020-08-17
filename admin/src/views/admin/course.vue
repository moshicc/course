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

    <div class="row">
      <div v-for="course in courses" class="col-md-4">
        <div class="thumbnail search-thumbnail">
        <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg" />
          <img v-show="course.image" class="media-object" v-bind:src="course.image" />
        <div class="caption">
          <div class="clearfix">
            <span class="pull-right label label-primary info-label">
              {{COURSE_LEVEL | optionKV(course.level)}}
            </span>
            <span class="pull-right label label-primary info-label">
              {{COURSE_CHARGE | optionKV(course.charge)}}
            </span>
            <span class="pull-right label label-primary info-label">
              {{COURSE_STATUS | optionKV(course.status)}}
            </span>
          </div>

          <h3 class="search-title">
            <a href="#" class="blue">{{course.name}}</a>
          </h3>
          <p>
            <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>
          </p>
          <p>{{course.summary}}</p>
          <p>
            <span class="badge badge-info">{{course.id}}</span>
            <span class="badge badge-info">排序：{{course.sort}}</span>
            <span class="badge badge-info">时长：{{course.time | formatSecond}}</span>
          </p>
          <p>
            <button v-on:click="toChapter(course)" class="btn btn-xs btn-primary">
              大章
            </button>
            <button v-on:click="edit(course)" class="btn btn-xs btn-info">
              编辑
            </button>
            <button v-on:click="del(course.id)" class="btn btn-xs btn-danger">
              删除
            </button>
          </p>

        </div>
        </div>
      </div>
    </div>

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
                                  <label class="col-sm-2 control-label">分类</label>
                                  <div class="col-sm-10">
                                    <ul id="tree" class="ztree"></ul>
                                  </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">名称</label>
                                    <div class="col-sm-10">
                                        <input v-model="course.name" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">概述</label>
                                    <div class="col-sm-10">
                                        <input v-model="course.summary" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">时长</label>
                                    <div class="col-sm-10">
                                        <input v-model="course.time" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">价格（元）</label>
                                    <div class="col-sm-10">
                                        <input v-model="course.price" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">封面</label>
                                    <div class="col-sm-10">
                                        <input v-model="course.image" class="form-control">
                                    </div>
                                </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">级别</label>
                        <div class="col-sm-10">
                            <select v-model="course.level" type="text" class="form-control" >
                                <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">收费</label>
                        <div class="col-sm-10">
                            <select v-model="course.charge" type="text" class="form-control" >
                                <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                            <select v-model="course.status" type="text" class="form-control" >
                                <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                            </select>
                        </div>
                    </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">报名数</label>
                                    <div class="col-sm-10">
                                        <input v-model="course.enroll" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">顺序</label>
                                    <div class="col-sm-10">
                                        <input v-model="course.sort" class="form-control">
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
        name: "business-course",
        data: function(){
            return {
                course: {},
                courses: [],
                COURSE_LEVEL: COURSE_LEVEL,
                COURSE_CHARGE: COURSE_CHARGE,
                COURSE_STATUS: COURSE_STATUS,
                categorys:[],
            }
        },
        mounted:function(){
            let  _this = this;
            _this.$refs.pagination.size=5;
            //初始化
            _this.allCategory();
            _this.list(1);
            //sidebar激活样式 方法一
            //this.$parent.activeSidebar("business-course-sidebar");
        },
        methods:{
            /**
             * 点击【新增】
             */
            add(){
                let _this = this;
                _this.course ={};
                $("#form-modal").modal("show")
            },
            /**
             *点击【编辑】
             */
            edit(course){
                let _this = this;
                _this.course =$.extend({},course);
                $("#form-modal").modal("show")
            },
            /**
             * 列表查询
             * @param page
             */
            list(page) {
                let _this =this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/admin/course/list",{
                    page:page,
                    size:_this.$refs.pagination.size,
                }).then((resopnes)=>{
                    Loading.hide();
                    //response.data 获得的就是后端统一传来的responseDto，里面有success，code，message，content
                    let resp = resopnes.data;
                    //resp.content 就是封装的pageDto ，里面有页码num，页数size，内容list 等
                    _this.courses = resp.content.list;
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
                if (1 != 1
                        ||!Validator.require(_this.course.name,"名称")
                    ||!Validator.length(_this.course.name,"名称",1,50)
                    ||!Validator.length(_this.course.summary,"概述",1,2000)
                    ||!Validator.length(_this.course.image,"封面",1,100)
                ) {
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/admin/course/save", _this.course).then((resopnes)=>{
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
                Confirm.show("删除课程后不可恢复，确认删除？",function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER+"/business/admin/course/delete/"+id).then((resopnes)=>{
                        Loading.hide();
                        let resp = resopnes.data;
                        if(resp.success){
                            //删除成功，并且刷新list
                            _this.list(1);
                            Toast.success("删除成功！");
                        }
                    })
                });

            },
            /**
             *点击大章
             */
            toChapter(course){
                let _this = this;
              //把course缓存起来（组件（页面）之间传输数据可以用h5原生的localStorage，SessionStorage，也可以用js，vuex store，但是刷新会丢失）
                SessionStorage.set("course",course);
                //跳转
                _this.$router.push("/business/chapter")
            },
            /**
             * 列表查询
             * @param
             */
            allCategory() {
                let _this =this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+"/business/admin/category/all").then((resopnes)=>{
                    Loading.hide();
                    //response.data 获得的就是后端统一传来的responseDto，里面有success，code，message，content
                    let resp = resopnes.data;
                    _this.categorys = resp.content;
                    //查到数据后 在initTree
                    _this.initTree();
                })
            },
            /**
             * 初始化Tree
             */
            initTree() {
                let _this = this;
                let setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            //将zTree的变量和 查询出来的数据名对应起来
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "00000000",
                            enable: true
                        }
                    }
                };

                let zNodes =_this.categorys;
                $.fn.zTree.init($("#tree"), setting, zNodes);
            }
        }
    }
</script>

<style scoped>
  .caption h3{
    font-size: 20px;
  }
</style>