<template>
  <div id="app"
       style="overflow: hidden;">

    <!-- 主体 -->
    <el-container class="container">
      <!-- <el-header>
        <h1 v-if="message.userAppName">{{ acceptUser }}</h1>
      </el-header> -->
      <!-- 侧边 -->
      <el-aside width="220px">
        <div class="sidebar_content">
          <!-- 显示有聊天记录的好友列表 -->
          <div v-for="(item, index) in messageList"
               :key="item.petAssistanceUserId">
            <el-menu class="aside_menu1">
              <el-menu-item :index="item.petAssistanceUserId"
                            @click="handleClick(index)">
                <img :src="item.userPhotoUrl"
                     alt="avatar"
                     class="sidebar_contact_avatar">
                <span class="sidebar_contact_name">{{ item.userAppName }}</span>
                <img :src="require('../../assets/icon/unRead.png')"
                     v-if="item.toUserId==user.petAssistanceUserId && !item.isRead"
                     alt="avatar"
                     class="is_read"> &nbsp;&nbsp;
              </el-menu-item>
            </el-menu>
          </div>
        </div>
      </el-aside>
      <!-- 内容 -->
      <el-main>
        <div class="chat-content">
          <!-- recordContent 聊天记录数组-->
          <ul class="infinite-list"
              v-infinite-scroll="load"
              style="overflow:auto;height:620px"
              v-if="selectUser.petAssistanceUserId != '' ">
            <div v-for="(itemc,indexc) in messageHistory"
                 :key="indexc"
                 style="margin-bottom: 0px;">
              <!-- 对方 -->
              <div class="word"
                   v-if="itemc.fromUserId == selectUser.petAssistanceUserId">
                <img :src="selectUser.userPhotoUrl">
                <div class="info">
                  <p class="time">{{selectUser.userAppName}} {{itemc.messageDate | dateFormat}}</p>
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
                    v-if="selectUser.petAssistanceUserId != '' "
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="message.messageContent"
                    @keyup.enter.native="sendMessage"
                    style="flex: 1;"></el-input>
          <el-button type="primary"
                     v-if="selectUser.petAssistanceUserId != '' "
                     @click="sendMessage"
                     style="margin-left: 10px;">发送</el-button>
        </div>

      </el-main>
    </el-container>
  </div>
</template>
  
  <script>
// 导入组件

export default {
  name: "App",
  data() {
    return {
      user: {
        userAppName: window.localStorage.getItem("userAppName"),
        petAssistanceUserId: window.localStorage.getItem("petAssistanceUserId"),
        userPhotoUrl: window.localStorage.getItem("userPhotoUrl"),
      },
      selectUser: {
        userAppName: "",
        petAssistanceUserId: "",
        userPhotoUrl: "",
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
      receiveMessage: {
        petAssistanceMessageId: "",
        fromUserId: "",
        toUserId: "",
        messageContent: "",
        isRead: false,
        messageDate: "",
      },
      messageList: [],
      messageHistory: [],
    };
  },
  methods: {
    handleClick(index) {
      this.selectUser.petAssistanceUserId =
        this.messageList[index].petAssistanceUserId;
      this.selectUser.userAppName = this.messageList[index].userAppName;
      this.selectUser.userPhotoUrl = this.messageList[index].userPhotoUrl;
      console.log(this.selectUser);
      // 变更message
      this.message.fromUserId = this.user.petAssistanceUserId;
      this.message.toUserId = this.selectUser.petAssistanceUserId;
      //变更未读图标提示
      this.messageList[index].isRead = true;
      // 变更聊天框
      this.$http({
        url:
          "/message/getHistory/" +
          this.user.petAssistanceUserId +
          "/" +
          this.selectUser.petAssistanceUserId,
        method: "get",
      })
        .then((res) => {
          console.log(res);
          this.messageHistory = res.data.data;
        })
        .catch((res) => {
          console.log(res);
        });
    },
    // websocket收到消息
    WebSocket_OnMesage(msg) {
      console.log(msg);
      this.receiveMessage = JSON.parse(msg.data);
      // 假设 jsonObject 是你的 JSON 对象，dateProperty 是需要转换的属性名
      this.receiveMessage.messageDate = this.receiveMessage.messageDate.time;
      console.log(this.receiveMessage);
      // 判断是否是当前聊天对象
      if (
        this.receiveMessage.fromUserId == this.selectUser.petAssistanceUserId
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
    },
    getMessageList() {
      this.$http({
        url: "/message/getMessageList/" + this.user.petAssistanceUserId,
        method: "get",
      })
        .then((res) => {
          console.log(res);
          this.messageList = res.data.data;
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
  },
  mounted() {
    this.$WebSocket.WebSocketBandMsgReceivedEvent(this.WebSocket_OnMesage);
    this.getMessageList();
  },
};
</script>
  
<style lang="less">
.scrollbar {
  max-height: 100%;
  overflow-x: hidden;
  overflow-y: scroll;
}
.container {
  width: 93%;
  height: 95%;
  position: absolute;
  top: 71px;
  left: 50%;
  transform: translateX(-50%);
  // border: 1px solid red;
}
.is_read {
  width: 15px;
  height: 15px;
  position: absolute;
  right: 10px; /* 靠右对齐 */
  top: 50%; /* 垂直居中，根据需要调整 */
  transform: translateY(-50%);
}
// 侧边栏
.aside_menu1 {
  height: 100%;
  font-size: 16px;
  font-weight: 600;
  color: #333;
  overflow: hidden;
  position: relative;
}
.sidebar_contact {
  display: flex;
  align-items: center;
  padding: 8px;
  cursor: pointer;
}

.sidebar_contact_avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 12px;
}

.sidebar_contact_name {
  font-size: 14px;
  color: #333;
}
.chat-content {
  width: 100%;
  height: 650px;
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