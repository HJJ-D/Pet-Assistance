<template>
  <div>
    <div class="opt">
      <el-menu default-active="1"
               mode="horizontal">
        <el-menu-item index="1"
                      @click="changeShow('求助')">求助</el-menu-item>
        <el-menu-item index="2"
                      @click="changeShow('社区')">社区</el-menu-item>
      </el-menu>
    </div>
    <!-- 核心内容 -->
    <div class="waterful">
      <div class="watch_item"
           v-for="item in show_data"
           :key="item.petAssistanceHelpId">
        <img :src="item.helpCover"
             alt=""
             srcset=""
             width="175px"
             height="auto"
             @click="openInfo(item.petAssistanceHelpId)">

        <div class="watch_item_middle"><span>{{ item.helpTitle }}</span></div>

        <div class="watch_item_bottom">
          <div>
            <el-avatar :src="item.userPhotoUrl"
                       size="small"> user </el-avatar>
          </div>
          <div>
            <span class="name">{{ item.userAppName }}</span>
          </div>
          <!-- <div class="zan">
            <i class="el-icon-star-off"></i>{{item.collectionnum}}
          </div> -->
        </div>
      </div>
    </div>

    <!-- 详情内容，对话框 -->
    <el-dialog :visible.sync="dialogVisible"
               top="50px"
               width="1000px"
               height="80%"
               :before-close="handleClose"
               append-to-body
               style="border-radius: 20px;">

      <div class="dialog_conten">

        <!-- 媒体区域，v-if 自动选择图片或者视频 -->
        <div class="dialog_conten_media">

          <!-- 自动缩放，居中定位 -->
          <div class="media_position">
            <!-- 展示图片 -->
            <div v-if="!(targetData.helpImage.length == 0)">
              <el-carousel width="516px"
                           height="600px"
                           :autoplay="true">
                <el-carousel-item v-for="item in targetData.helpImage"
                                  :key="item">
                  <el-image :src="item"
                            fit="contain"
                            style="display: block; margin: auto;"></el-image>
                </el-carousel-item>
              </el-carousel>
            </div>

            <!-- 展示视频 -->
            <div v-if="!(targetData.helpVideo == null || targetData.helpVideo == '' )"
                 style="
                     position: absolute;
                     top: 0;
                     left: 50%;
                     transform: translateX(-50%);
              ">
              <video-player :options="videoOptions" />
            </div>
          </div>
        </div>

        <!-- 内容信息区域 -->
        <div class="dialog_conten_imfo">

          <!-- 第一行：{头像 姓名，关注} -->
          <div class="one_imfo">
            <el-avatar :src="targetData.userPhotoUrl"
                       size="large"
                       style="margin-left: 20px;"> user </el-avatar>
            <div class="one_imfo_username_span">
              <span>{{ targetData.userAppName }}</span>
            </div>
            <!-- 私聊 -->
            <div style="margin-left: 10px; float: right;">
              <el-popover placement="top-start"
                          title="进行私聊吧！"
                          width="200"
                          trigger="hover"
                          content="有一些问题？想要提供帮助？来进行私聊吧！">
                <el-button slot="reference"
                           @click="privateChat">私聊</el-button>
              </el-popover>
            </div>
          </div>

          <!-- 第二行（可滚动内容）-->
          <div class="two_imfo">

            <!-- 内容: {标题，内容, 创建时间} -->
            <div>
              <div class="two_imfo_title">
                {{ targetData.helpTitle }}
              </div>
              <div class="two_imfo_content">
                {{ targetData.helpContent }}
              </div>
              <div class="two_imfo_created">
                {{ targetData.helpPublishTime | dateFormat}}
              </div>
              <!-- <div class="two_imfo_created">共计 {{ targetData.comment.length }} 条评论</div> -->
              <hr>
            </div>

            <!-- 评论：{共计评论数，评论者头像，评论信息} -->
            <div>
              <div v-for="item in targetData.comment"
                   :key="item.id">
                <div style="color: #303133;font-size: 15px;font-weight: 800;">
                  <el-avatar :src="item.userPhotoUrl"
                             size="small"> user </el-avatar>
                  {{ item.userAppName }}
                </div>
                <div>
                  {{ item.commentContent }}
                </div>
                <div class="two_imfo_created">
                  {{ item.commentDate | dateFormat}}
                </div>
                <hr>
              </div>
              <div style="text-align: center;"> -THE END-</div>
            </div>
          </div>

          <!-- 第三行：{点赞，分析 发起评论} -->
          <div class="three_comment">
            <hr>
            <div class="three_icon">
              <i :class="zan_class"
                 @click="change_zan"></i> <span>点赞数 {{ targetData.likeNum }}</span> &nbsp;&nbsp;&nbsp;&nbsp;
              <i :class="star_class"
                 @click="change_star"></i> <span>收藏数 {{ targetData.collectNum }}</span>
            </div>

            <div class="three_input">
              <el-input placeholder="评论一下吧~"
                        v-model="comment_text">
                <el-button slot="append"
                           icon="el-icon-chat-dot-round"
                           @click="sendComment"></el-button>
              </el-input>
            </div>

          </div>

        </div>
      </div>
    </el-dialog>
    <el-drawer title="私聊"
               :visible.sync="rebate_chat_drawer"
               :with-header="true"
               append-to-body>
      <div class="chat-content-2">
        <ul class="infinite-list"
            v-infinite-scroll="load"
            style="overflow:auto;height:800px"
            v-if="targetData.petAssistanceUserId != '' ">
          <div v-for="(itemc,indexc) in messageHistory"
               :key="indexc"
               style="margin-bottom: 0px;">
            <!-- 对方 -->
            <div class="word"
                 v-if="itemc.fromUserId == targetData.petAssistanceUserId">
              <img :src="targetData.userPhotoUrl">
              <div class="info">
                <p class="time">{{targetData.userAppName}} {{itemc.messageDate | dateFormat}}</p>
                <div class="info-content">{{itemc.messageContent}}</div>
              </div>
            </div>
            <!-- 我的 -->
            <div class="word-my"
                 v-else>
              <div class="info">
                <p class="time">{{user.userAppName}} {{itemc.messageDate | dateFormat }}</p>
                <div class="info-content">{{itemc.messageContent}}</div>
              </div>
              <img :src="user.userPhotoUrl">
            </div>
          </div>
        </ul>
      </div>
      <div class="chat-input"
           style="display: flex; align-items: flex-start;">
        <el-input type="textarea"
                  :rows="2"
                  placeholder="请输入内容"
                  v-model="message.messageContent"
                  @keyup.enter.native="sendMessage"
                  style="flex: 1;"></el-input>
        <el-button type="primary"
                   @click="sendMessage"
                   style="margin-left: 10px;">发送</el-button>
      </div>
    </el-drawer>
  </div>
</template>
<script>
import VideoPlayer from "@/components/explore/VideoPlayer.vue";
// 在组件文件中导入Vue模块
import Vue from "vue";

export default {
  name: "VideoExample",
  components: {
    VideoPlayer,
  },
  data() {
    return {
      user: {
        userAppName: window.localStorage.getItem("userAppName"),
        petAssistanceUserId: window.localStorage.getItem("petAssistanceUserId"),
        userPhotoUrl: window.localStorage.getItem("userPhotoUrl"),
        userArea: window.localStorage.getItem("userArea"),
      },
      message: {
        petAssistanceMessageId: "",
        fromUserId: "",
        toUserId: "",
        messageContent: "",
        isRead: false,
        petAssistanceUserId: "",
        userAppName: "",
        userPhotoUrl: "",
      },
      messageHistory: [
        {
          petAssistanceMessageId: "",
          fromUserId: "3e975938-25dd-4d92-b785-7c4eeaf2a3d7",
          toUserId: "3e975938-25dd-4d92-b785-7c4eeaf2a3d7",
          messageContent: "123123123123",
          isRead: false,
          petAssistanceUserId: "",
          messageDate: "1",
        },
      ],
      rebate_chat_drawer: false,
      like: {
        likeId: "",
        petAssistanceHelpId: "",
        petAssistanceUserId: window.localStorage.getItem("petAssistanceUserId"),
        likeDeleted: true,
      },
      collect: {
        collectId: "",
        petAssistanceHelpId: "",
        petAssistanceUserId: window.localStorage.getItem("petAssistanceUserId"),
        collectDeleted: true,
      },
      // 点赞 收藏的样式切换
      zan_class: "el-icon-circle-check",
      star_class: "el-icon-star-off",
      // 笔记详情
      targetData: {
        helpPublishTime: null,
        userAppName: "",
        helpContent: "",
        comment: [],
        helpImage: [],
        videourl: null,
        userPhotoUrl: "",
        helpTitle: "",
        likeNum: 0,
        collectNum: 0,
        collectDeleted: true,
        likeDeleted: true,
        isHelp: false,
      },
      // videoJS m3u8播放器
      videoOptions: {
        autoplay: true,
        controls: true,
        sources: [
          {
            src: require("@/assets/video/测试视频.mp4"),
            type: "application/x-mpegURL",
          },
          {
            src: require("@/assets/video/测试视频.mp4"),
            type: "video/mp4",
          },
        ],
      },
      headphoto: window.sessionStorage.getItem("headphoto"),
      // 对话框的展示与否
      dialogVisible: false,
      data: {
        helpData: [],
        communityData: [],
      },
      show_data: [],
      comment_text: "",
    };
  },
  watch: {
    // 当targetData的数据发生变化，实时更新videoJS的videoOptions数据
    "targetData.videourl"(newVal, oldVal) {
      Vue.set(this.videoOptions.sources, 0, {
        src: newVal,
        type: "video/mp4",
      });
    },
  },
  mounted() {
    // 有网络时解开
    this.getAssistance();
    this.$WebSocket.WebSocketBandMsgReceivedEvent(this.WebSocket_OnMesage);
    // 一个测试，m3u8，到时候改到触发事件
    // this.videoOptions.sources[0].src = this.targetData.videourl vue2响应式数据修改数组应慎重
    // Vue.set(this.videoOptions.sources, 0, {
    //   src: this.targetData.videourl,
    //   type: "video/mp4",
    // });
    // console.log(this.videoOptions)
  },
  methods: {
    // websocket收到消息
    WebSocket_OnMesage(msg) {
      console.log(msg);
      //判断是否打开聊天栏
      if (this.rebate_chat_drawer) {
        this.receiveMessage = JSON.parse(msg.data);
        // 假设 jsonObject 是你的 JSON 对象，dateProperty 是需要转换的属性名
        this.receiveMessage.messageDate = this.receiveMessage.messageDate.time;
        console.log(this.receiveMessage);
        // 判断是否是当前聊天对象
        if (
          this.receiveMessage.fromUserId == this.targetData.petAssistanceUserId
        ) {
          this.messageHistory.push(this.receiveMessage);
        } else {
          // 如果不是当前聊天对象，更新左侧的聊天列表
          this.messageList.forEach((item, index) => {
            if (item.petAssistanceUserId == this.receiveMessage.fromUserId) {
              this.messageList.splice(index, 1);
            }
          });
        }
      } else {
        this.$message.success("你有一条新消息");
      }
    },
    privateChat() {
      // 变更message
      this.message.fromUserId = this.user.petAssistanceUserId;
      this.message.toUserId = this.targetData.petAssistanceUserId;
      // 变更聊天框
      this.$http({
        url:
          "/message/getHistory/" +
          this.user.petAssistanceUserId +
          "/" +
          this.message.toUserId,
        method: "get",
      })
        .then((res) => {
          console.log(res);
          this.messageHistory = res.data.data;
        })
        .catch((res) => {
          console.log(res);
        });
      this.rebate_chat_drawer = true;
    },
    // 获取首页数据
    getAssistance() {
      this.$http({
        url: "/assistance/recommend",
        method: "post",
        data: this.user,
      })
        .then((res) => {
          this.data.helpData = res.data.data.recommend;
          this.data.communityData = res.data.data.community;
          this.show_data = this.data.helpData;
          console.log(show_data);
        })
        .catch((res) => {
          console.log(res);
        });
    },
    sendMessage() {
      this.message.messageDate = new Date();
      this.$http({
        url: "/message/sendMessage",
        method: "post",
        data: this.message,
      })
        .then((res) => {
          this.messageHistory.push(Object.assign({}, this.message));
          this.message.messageContent = "";
        })
        .catch((res) => {
          console.log(res);
        });
    },
    // 查看详情
    openInfo(id) {
      const dto = {
        petAssistanceHelpId: id,
        petAssistanceUserId: window.localStorage.getItem("petAssistanceUserId"),
      };
      this.like.petAssistanceHelpId = id;
      this.collect.petAssistanceHelpId = id;
      // console.log(id)
      this.$http({
        url: "/assistance/getAssistance",
        method: "post",
        data: dto,
      })
        .then((res) => {
          console.log(res);
          this.targetData = res.data.data;
          this.like.likeDeleted = res.data.data.likeDeleted;
          this.collect.collectDeleted = res.data.data.collectDeleted;
          this.like.likeId = res.data.data.likeId;
          this.collect.collectId = res.data.data.collectId;
          this.targetData.helpImage = this.targetData.helpImage.split(",");
          if (res.data.data.likeDeleted) {
            this.zan_class = "el-icon-circle-check";
          } else {
            this.zan_class = "el-icon-success";
          }
          if (res.data.data.collectDeleted) {
            this.star_class = "el-icon-star-off";
          } else {
            this.star_class = "el-icon-star-on";
          }
        })
        .catch((res) => {
          console.log(res);
        });

      this.dialogVisible = true;
    },

    // 关闭对话框
    handleClose(done) {
      done();
    },

    // 切换 瀑布流内容展示
    changeShow(type_str) {
      if ("求助" == type_str) {
        this.show_data = this.data.helpData;
      } else {
        this.show_data = this.data.communityData;
      }
    },

    // 提供给父组件调用的搜索方法
    children_search_note(search_text) {
      // console.log('调用成功')
      // console.log(search_text)
      this.$http({
        url: "/found/search",
        method: "get",
        params: {
          param: search_text,
        },
      })
        .then((res) => {
          this.show_data = res.data.data;
        })
        .catch((res) => {
          console.log(res);
        });
    },

    // 切换点赞
    change_zan() {
      this.like.likeDeleted = !this.like.likeDeleted;
      this.$http({
        url: "/like/like",
        method: "post",
        data: this.like,
      })
        .then((res) => {
          var status = "";
          status = res.data.status;
          console.log(status);
          if (status != 200) {
            this.$message.error("网络请求失败，请重试或联系工作人员");
          } else {
            // 取消点赞
            if ("el-icon-success" == this.zan_class) {
              this.zan_class = "el-icon-circle-check";
              this.targetData.likeNum = this.targetData.likeNum - 1;
            }
            // 启用点赞
            else {
              this.zan_class = "el-icon-success";
              this.targetData.likeNum = this.targetData.likeNum + 1;
            }
          }
        })
        .catch((res) => {
          console.log(res);
        });
    },

    // 切换收藏
    change_star() {
      this.collect.collectDeleted = !this.collect.collectDeleted;
      this.$http({
        url: "/collect/collect",
        method: "post",
        data: this.collect,
      })
        .then((res) => {
          var status = "";
          status = res.data.data;
          if (status == "") {
            this.$message.error("网络请求失败，请重试或联系工作人员");
          } else {
            // 取消收藏
            if ("el-icon-star-on" == this.star_class) {
              this.star_class = "el-icon-star-off";
              this.targetData.collectNum = this.targetData.collectNum - 1;
            }
            // 启用收藏
            else {
              this.star_class = "el-icon-star-on";
              this.targetData.collectNum = this.targetData.collectNum + 1;
            }
          }
        })
        .catch((res) => {
          console.log(res);
        });
    },

    // 发送评论
    sendComment() {
      this.$http({
        url: "/comment/comment",
        method: "post",
        data: {
          //键值对
          petAssistanceUserId: this.user.petAssistanceUserId,
          petAssistanceHelpId: this.targetData.petAssistanceHelpId,
          commentContent: this.comment_text,
        },
      })
        .then((res) => {
          // 评论成功，重新加载数据，清空评论内容
          if ("保存成功" == res.data.data) {
            // this.openInfo(123456);
            this.comment_text = "";
          }
        })
        .catch((res) => {
          console.log(res);
        });
    },
  },
};
</script>
<style lang="less">
.opt {
  height: 40px;
  margin-bottom: 5px;
  .el-menu-item {
    height: 40px;
    line-height: 40px;
    border-radius: 20px;
    border: none;
    font-size: 16px;
    background-color: #fff;
    color: rgba(51, 51, 51, 0.8);
  }
  .el-menu-item.is-active {
    height: 40px;
    line-height: 40px;
    border-radius: 20px;
    border: none;
    background-color: #f8f8f8 !important;
    color: #333;
    font-weight: 600;
  }
}

// 去除下划线，文本装饰线
.opt .el-menu--horizontal > .el-menu-item.is-active {
  border-bottom: none;
  text-decoration: none;
}
.opt .el-menu.el-menu--horizontal {
  border-bottom: none;
}
.opt .el-menu--horizontal.el-menu {
  border-bottom: none;
}

// 瀑布流布局
.waterful {
  padding-left: 10px;
  column-count: 5; //想要排成的列数
  column-gap: 0; // 列间距
  margin-bottom: 150px;
}

.watch_item {
  break-inside: avoid; //避免“瀑布流”内部的内容被断开
  width: 175px;
  margin-bottom: 12px;
  border-radius: 5px;
  // padding-bottom: 5px;
  img {
    border-radius: 10px;
  }
  .watch_item_middle {
    padding: 5px;
  }
  .watch_item_bottom {
    padding: 5px;
    height: 25px;
    line-height: 25px;
    text-align: center;
    font-weight: 500;
    font-size: 14px;
    color: #333;
    display: flex;
    justify-content: space-around;
    .name {
      padding-left: 10px;
      color: #666666;
      font-size: 12px;
    }
    .zan {
      padding-left: 10px;
    }
  }
}

.watch_item:hover {
  cursor: pointer;
  transform: scale(1.02);
  transition-duration: 0.1s;
  transition-timing-function: linear;
  transition-delay: 0s;
  transition-property: all;
  box-shadow: 5px 5px 20px 5px rgba(0, 0, 0, 0.15);
  filter: contrast(1.25);
}

.el-dialog {
  border-radius: 15px;
  // overflow: hidden;
}

// 笔记详情对话框
.dialog_conten {
  width: 100%;
  height: 600px;
  // display: flex;
  position: relative;

  // 笔记媒体区域
  .dialog_conten_media {
    width: 70%;
    height: 100%;
    // background-color: burlywood;
    position: absolute;
    left: 0;
    top: 0;
    .media_position {
      border-radius: 5px;
      overflow: hidden;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 50%;
      left: 0;
      transform: translateY(-50%);
      // background-color: #333;
    }
  }
  // 笔记信息
  .dialog_conten_imfo {
    width: 40%;
    height: 100%;
    // background-color: cadetblue;
    // border: 1px solid red;
    position: absolute;
    right: 0;
    top: 0;
    // 第一行
    .one_imfo {
      width: 100%;
      height: 40px;
      line-height: 40px;
      position: relative;
      // 头像 写定 行内样式

      // 用户名
      .one_imfo_username_span {
        height: 40px;
        line-height: 40px;
        color: #303133;
        font-size: 23px;
        font-weight: 800;
        position: absolute;
        left: 30%;
        top: 0;
        transform: translateX(-50%);
      }
      // 关注按钮
      .el-button.el-button--primary.is-round {
        position: absolute;
        right: 20px;
        top: 50%;
        transform: translateY(-50%);
      }
    }
    // 第二行
    // 去除滚动栏，保留滚动效果
    .two_imfo::-webkit-scrollbar {
      width: 0;
      height: 0;
      background-color: transparent;
    }
    .two_imfo {
      box-sizing: border-box;
      padding: 10px;
      width: 100%;
      height: 75%;
      // background-color: #f2c0c0;
      overflow-y: scroll;
      .two_imfo_title {
        color: rgb(51, 51, 51);
        display: block;
        font-family: system-ui, -apple-system, "Segoe UI", Roboto, Ubuntu,
          Cantarell, "Noto Sans", sans-serif, BlinkMacSystemFont,
          "Helvetica Neue", Arial, "PingFang SC", "PingFang TC", "PingFang HK",
          "Microsoft Yahei", "Microsoft JhengHei";
        font-size: 20px;
        font-weight: 600;
        text-align: center;
      }
      .two_imfo_content {
        margin-top: 6px;
        color: rgb(51, 51, 51);
        font-family: system-ui, -apple-system, "Segoe UI", Roboto, Ubuntu,
          Cantarell, "Noto Sans", sans-serif, BlinkMacSystemFont,
          "Helvetica Neue", Arial, "PingFang SC", "PingFang TC", "PingFang HK",
          "Microsoft Yahei", "Microsoft JhengHei";
        font-size: 16px;
        font-weight: 400;
      }
      .two_imfo_created {
        margin-top: 6px;
        color: rgba(51, 51, 51, 0.6);
        font-family: system-ui, -apple-system, "Segoe UI", Roboto, Ubuntu,
          Cantarell, "Noto Sans", sans-serif, BlinkMacSystemFont,
          "Helvetica Neue", Arial, "PingFang SC", "PingFang TC", "PingFang HK",
          "Microsoft Yahei", "Microsoft JhengHei";
        font-size: 12px;
      }
    }

    // 第三行
    .three_comment {
      // position: absolute;
      // bottom: 0;
      width: 100%;
      height: 100px;
      // background-color: #f2c0c0;
      position: relative;
      padding-left: 10px;
      .three_icon {
        font-size: 26px;
        font-weight: 800;
        height: 36px;
        line-height: 36px;
        span {
          font-size: 10px;
          font-weight: 400;
          line-height: 36px;
        }
      }
      .three_input {
        position: absolute;
        bottom: 0px;
        width: 96%;
        .el-input__inner {
          border-top-left-radius: 15px;
          border-bottom-left-radius: 15px;
        }
        .el-input-group__append {
          border-top-right-radius: 15px;
          border-bottom-right-radius: 15px;
        }
      }
    }
  }
}
.chat-content-2 {
  width: 100%;
  height: 90%;
  padding: 20px;
  .word {
    display: flex;
    margin-bottom: 20px;
    img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
    .info {
      margin-left: 10px;
      .time {
        font-size: 12px;
        color: rgba(51, 51, 51, 0.8);
        margin: 0;
        height: 20px;
        line-height: 20px;
        margin-top: -5px;
      }
      .info-content {
        padding: 10px;
        font-size: 14px;
        background: #fff;
        position: relative;
        margin-top: 8px;
      }
      //小三角形
      .info-content::before {
        position: absolute;
        left: -8px;
        top: 8px;
        content: "";
        border-right: 10px solid #fff;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
      }
    }
  }

  .word-my {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 20px;
    img {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
    .info {
      width: 90%;
      margin-left: 10px;
      text-align: right;
      .time {
        font-size: 12px;
        color: rgba(51, 51, 51, 0.8);
        margin: 0;
        height: 20px;
        line-height: 20px;
        margin-top: -5px;
        margin-right: 10px;
      }
      .info-content {
        max-width: 70%;
        padding: 10px;
        font-size: 14px;
        float: right;
        margin-right: 10px;
        position: relative;
        margin-top: 8px;
        background: #a3c3f6;
        text-align: left;
      }
      //小三角形
      .info-content::after {
        position: absolute;
        right: -8px;
        top: 8px;
        content: "";
        border-left: 10px solid #a3c3f6;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
      }
    }
  }
}
</style>