<template>
  <div>
    <div class="pubnote">
      <el-menu :default-active="pic_or_vlog"
               mode="horizontal"
               @select="handleSelect">
        <el-menu-item index="pub_picture">发布图文</el-menu-item>
        <el-menu-item index="pub_vlog">发布视频</el-menu-item>
      </el-menu>

      <!-- 发布图文 -->
      <div class="note"
           v-if="pic_or_vlog == 'pub_picture' ">
        <div class="note_real">

          <div class="cencen">
            <span>封面</span>
            <el-button type="text"> &nbsp; +上传封面</el-button> &nbsp;&nbsp; <em style="font-size: small; font-weight: 100;">（漂亮的封面可以吸引更多集美、集帅哦）</em>
            <br>
            <el-upload class="avatar-uploader"
                       action=""
                       :http-request="upload_PictureNode_surfacePicture"
                       :show-file-list="false"
                       :on-success="handleAvatarSuccess_pictureNode"
                       :before-upload="beforeAvatarUpload_pictureNode">
              <img v-if="pictureNode.helpCover"
                   :src="pictureNode.helpCover"
                   class="avatar">
              <i v-else
                 class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>

          <div class="cencen">
            <span>上传图片</span> <br> <br>
            <el-upload class="upload-demo"
                       action=""
                       :http-request="upload_PictureNode_pictureurl"
                       :on-preview="handlePreview"
                       :on-remove="handleRemove"
                       :file-list="fileList"
                       list-type="picture">
              <el-button size="small"
                         type="primary">点击上传</el-button>
              <div slot="tip"
                   class="el-upload__tip">只能上传jpg/png文件</div>
            </el-upload>
          </div>

          <div class="cencen">
            <el-input v-model="pictureNode.helpTitle"
                      placeholder="填写标题，可能会有更多赞哦~"></el-input>

            <div class="cencen">
              <el-input type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        placeholder="填写更全面的描述信息，让更多的人看到你吧！"
                        v-model="pictureNode.helpContent">
              </el-input>
            </div>
          </div>

          <div class="cencen">
            <el-radio v-model="pictureNode.tag"
                      label="喂养求助"
                      border>喂养求助</el-radio>
            <el-radio v-model="pictureNode.tag"
                      label="宠物日常"
                      border>宠物日常</el-radio>

          </div>

          <div class="cencen">
            <el-date-picker v-model="pictureNode.helpBeginTime"
                            type="datetime"
                            v-if="pictureNode.tag=='喂养求助'"
                            placeholder="选择上门喂养日期时间"
                            default-time="12:00:00">
            </el-date-picker>

          </div>

          <div class="pic_publish">
            <img :src="require('../../assets/mypic/pubNote.png')"
                 alt=""
                 srcset="">
            <div class="pub_pic_btn">
              <el-button type="primary"
                         @click="publish_PictureNode">发布</el-button>
              <el-button plain>取消</el-button>
            </div>
          </div>
        </div>

      </div>

      <!-- 发布视频 -->
      <div class="note"
           v-if="pic_or_vlog == 'pub_vlog' ">
        <div class="note_real">
          <div class="cencen">
            <span>封面</span>
            <el-button type="text"> &nbsp; +上传封面</el-button> &nbsp;&nbsp; <em style="font-size: small; font-weight: 100;">（漂亮的封面可以吸引更多集美、集帅哦）</em>
            <br>
            <el-upload class="avatar-uploader"
                       action=""
                       :http-request="upload_VideoNode_surfacePicture"
                       :show-file-list="false"
                       :on-success="handleAvatarSuccess_videoNode"
                       :before-upload="beforeAvatarUpload_videoNode">
              <img v-if="videoNote.surfacePicture"
                   :src="videoNote.surfacePicture"
                   class="avatar">
              <i v-else
                 class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
          <div class="cencen">
            <el-upload class="upload-demo"
                       drag
                       action=""
                       :file-list="videoFile"
                       :http-request="upload_VideoNode_videurl"
                       multiple>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <!-- <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div> -->
            </el-upload>
          </div>

          <div class="cencen">
            <el-input v-model="videoNote.title"
                      placeholder="填写标题，可能会有更多赞哦~"></el-input>

            <div class="cencen">
              <el-input type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        placeholder="填写更全面的描述信息，让更多的人看到你吧！"
                        v-model="videoNote.content">
              </el-input>
            </div>
          </div>

          <div class="cencen">
            <!-- // 推荐 书籍 美食 家居 穿搭 旅行 其他 -->
            <el-radio v-model="videoNote.tag"
                      label="书籍"
                      border>书籍</el-radio>
            <el-radio v-model="videoNote.tag"
                      label="美食"
                      border>美食</el-radio>
            <el-radio v-model="videoNote.tag"
                      label="家居"
                      border>家居</el-radio>
            <el-radio v-model="videoNote.tag"
                      label="穿搭"
                      border>穿搭</el-radio>
            <el-radio v-model="videoNote.tag"
                      label="旅行"
                      border>旅行</el-radio>
            <el-radio v-model="videoNote.tag"
                      label="其他"
                      border>其他</el-radio>
          </div>

          <div class="pic_publish">
            <img :src="require('../../assets/mypic/pubNote.png')"
                 alt=""
                 srcset="">
            <div class="pub_pic_btn">
              <el-button type="primary"
                         @click="publish_videoNode">发布</el-button>
              <el-button plain>取消</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 临时数据，用于展示笔记图片和展示视频数据
      videoFile: [
        // {name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
      ],
      fileList: [],
      pic_or_vlog: "pub_picture", // 切换发布笔记是图片还是视频
      // 图片内容预览用的对话框信息
      dialogPictureNode: {
        dialogImageUrl: "",
        dialogVisible: false,
      },
      // 求助图片笔记内容
      pictureNode: {
        // 发帖人uid
        petAssistanceUserId: window.localStorage.getItem("petAssistanceUserId"),
        //帖子文字内容
        helpContent: "",
        // 发帖时间
        helpPublishTime: "",
        // 上门喂养时间
        helpBeginTime: "",
        // 喂养状态
        helpStatus: 1,
        // 封面图片
        helpCover: "",
        // 标题
        helpTitle: "",
        // 笔记内容图片
        helpImage: "",
        //地区
        helpArea: window.localStorage.getItem("userArea"),
        // 标签
        tag: "宠物日常",
        // 是否为求助帖子
        isHelp: "",
      },
      // 视频笔记内容
      videoNote: {
        // 封面图片
        surfacePicture: "",
        // 标题
        title: "",
        // 笔记内容
        content: "",
        // 标签
        tag: "其他",
        // 上传图片
        videourl: "",
      },
    };
  },
  mounted() {},
  methods: {
    // 导航栏
    handleSelect(key, keyPath) {
      // console.log(key);
      this.pic_or_vlog = key;
    },
    // ===================================图片笔记===================================

    //-----------------------------------笔记内容图片-----------------------------------
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      // this.dialogPictureNode.dialogImageUrl = file.url
      // this.dialogPictureNode.dialogVisible = true;
      console.log(file);
    },
    // 上传笔记的图片 http://localhost:8080/release/uploadPicture （已经过时）
    // 用头像上传接口来实现单张图片上传 http://localhost:8080/user/headphoto
    upload_PictureNode_pictureurl(param) {
      const formdate = new FormData();
      formdate.append("file", param.file);
      const url = "/fileUpload";
      const headers = {
        "Content-Type": "multipart/form-data",
      };
      // axios.post的构造函数有顺序，不可乱
      this.$http
        .post(url, formdate, headers)
        .then((res) => {
          // console.log(data)
          this.$message.success("图片上传成功");
          // 临时的笔记图片，用于展示。
          // 等发布笔记的时候，再遍历fileList的url追加到pictureNode的pictureurl
          this.fileList.push({ name: param.file.name, url: res.data.data });
          console.log(this.fileList);
        })
        .catch((response) => {
          console.log(response);
        });
    },

    // -----------------------------------笔记封面图片-----------------------------------
    // 上传封面。也使用上传头像的接口
    upload_PictureNode_surfacePicture(param) {
      const formdate = new FormData();
      formdate.append("file", param.file);
      const url = "/fileUpload";
      const headers = {
        "Content-Type": "multipart/form-data",
      };
      // axios.post的构造函数有顺序，不可乱
      this.$http
        .post(url, formdate, headers)
        .then((res) => {
          // console.log(data)
          this.$message.success("封面上传成功");
          this.pictureNode.helpCover = res.data.data;
        })
        .catch((response) => {
          console.log(response);
        });
    },
    handleAvatarSuccess_pictureNode(res, file) {
      this.pictureNode.surfacePicture = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload_pictureNode(file) {},

    // -----------------------------------发布图文-----------------------------------
    // 发布图片笔记 http://localhost:8080/release/note
    publish_PictureNode() {
      for (var i = 0; i < this.fileList.length; i++) {
        if (i == this.fileList.length - 1) {
          this.pictureNode.helpImage += this.fileList[i].url;
        } else {
          this.pictureNode.helpImage += this.fileList[i].url + ",";
        }
      }
      let originalDate = new Date(); // 获取当前日期时间
      this.pictureNode.helpPublishTime = originalDate;
      if (this.pictureNode.tag == "喂养求助") {
        this.pictureNode.isHelp = true;
      } else {
        this.pictureNode.isHelp = false;
        this.pictureNode.helpStatus = "";
      }
      this.$http({
        url: "/assistance/newAssistance",
        data: this.pictureNode,
        method: "post",
      })
        .then((res) => {
          if (200 == res.data.status) {
            this.$message.success("上传成功！");
            // 成功后清空
            this.pictureNode = {
              petAssistanceUserId: "",
              helpContent: "",
              helpPublishTime: "",
              helpBeginTime: "",
              helpStatus: 1,
              helpCover: "",
              helpTitle: "",
              helpImage: "",
              tag: "宠物日常",
            };
            this.fileList = [];
          }
        })
        .catch((res) => {
          console.log(res);
          this.$message.error("笔记上传失败！");
        });
    },

    // ===================================视频笔记===================================

    // -----------------------------------笔记封面图片-------------------------------
    // 上传封面。也使用上传头像的接口
    upload_VideoNode_surfacePicture(param) {
      const formdate = new FormData();
      formdate.append("file", param.file);
      const url = "/fileUpload";
      const headers = {
        "Content-Type": "multipart/form-data",
      };
      // axios.post的构造函数有顺序，不可乱
      this.$http
        .post(url, formdate, headers)
        .then((res) => {
          // console.log(data)
          this.$message.success("封面上传成功");
          console.log(this.videoNote);
          this.videoNote.surfacePicture = res.data.data;
        })
        .catch((response) => {
          console.log(response);
        });
    },
    handleAvatarSuccess_videoNode(res, file) {
      this.videoNote.surfacePicture = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload_videoNode(file) {},

    // -----------------------------------视频上传-----------------------------------
    // 地址 http://localhost:8080/release/uploadVideo  name: param.file.name
    upload_VideoNode_videurl(param) {
      const formdate = new FormData();
      formdate.append("video", param.file);
      const url = "/release/uploadVideo";
      const headers = {
        "Content-Type": "multipart/form-data",
      };
      // axios.post的构造函数有顺序，不可乱
      this.$http
        .post(url, formdate, headers)
        .then((res) => {
          // console.log(data)
          this.$message.success("视频上传成功");
          this.videoFile.push({ name: param.file.name, url: res.data.data });
          this.videoNote.videourl = res.data.data;
          // console.log(this.videoNote)
        })
        .catch((response) => {
          console.log(response);
        });
    },
    // -----------------------------------发布视频-----------------------------------
    // 发布视频笔记 http://localhost:8080/release/note
    publish_videoNode() {
      for (var i = 0; i < this.fileList.length; i++) {
        this.pictureNode.pictureurl.push(this.fileList[i].url);
      }

      this.$http({
        url: "/release/note",
        data: this.videoNote,
        method: "post",
      })
        .then((res) => {
          if ("上传成功" == res.data.data) {
            this.$message.success("笔记上传成功！");
            // 成功后清空
            this.videoNote = {
              surfacePicture: "",
              title: "",
              content: "",
              tag: "其他",
              videourl: [],
            };
          }
        })
        .catch((res) => {
          console.log(res);
          this.$message.error("笔记上传失败！");
        });
    },
  },
};
</script>

<style lang="less" scoped>
.pubnote {
  width: 60%;
  height: 100%;
  background-color: white;
  border-radius: 20px;
  // overflow: hidden;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  overflow: scroll;
  .el-menu--horizontal.el-menu {
    padding-left: 8px;
    padding-right: 8px;
  }
}
.pubnote::-webkit-scrollbar {
  width: 0;
  height: 0;
  background-color: transparent;
}

.note {
  position: relative;
  width: 100%;
  height: 95%;
  .note_real {
    width: 96%;
    height: 96%;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    // background-color: aquamarine;
  }
}

.cencen {
  margin-top: 10px;
}

.pic_publish {
  position: relative;
  margin-top: 24px;
  background-color: rgb(238, 241, 248);
  width: 30%;
  height: 65px;
  line-height: 65px;
  border-radius: 10px;
  img {
    height: 65px;
    width: auto;
  }
  .pub_pic_btn {
    position: absolute;
    top: 50%;
    right: 20px;
    transform: translateY(-50%);
  }
}
</style>