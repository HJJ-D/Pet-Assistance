<template>
  <div id="app"
       style="overflow: hidden;">
    <router-view></router-view>
  </div>
</template>

<script>
// 导入组件
export default {
  name: "App",
  mounted() {
    // 因为我的页面有缓存机制，用户下次有可能直接打开某个登录后才能访问的页面 比如F5刷新了某个页面 需要重连
    // 又比如后端服务器因为什么原因突然中断了一下 也需要重新连接WebSocket
    // 每3秒检测一次websocket连接状态 未连接 则尝试连接 尽量保证网站启动的时候 WebSocket都能正常长连接
    setInterval(this.WebSocket_StatusCheck, 3000);
    // 绑定消息回调事件
    this.$WebSocket.WebSocketBandMsgReceivedEvent(this.WebSocket_OnMesage);
    //
    this.token = window.localStorage.getItem("token");
    this.userName = window.localStorage.getItem("userAppName");
  },
  data() {
    return {};
  },
  methods: {
    //实际消息回调事件
    WebSocket_OnMesage(msg) {
      console.log("收到服务器消息：", msg.data);
      this.$message.success("你有一条新消息");
      // window.dispatchEvent(
      //   new CustomEvent("onmessageWS", {
      //     detail: {
      //       data: msg.data,
      //     },
      //   })
      // );
    },

    // 1、WebSocket连接状态检测：
    WebSocket_StatusCheck() {
      if (
        !this.$WebSocket.WebSocketHandle ||
        this.$WebSocket.WebSocketHandle.readyState !== 1
      ) {
        console.log("Websocket连接中断，尝试重新连接:");
        this.WebSocketINI();
      }
    },

    // 2、WebSocket初始化：
    async WebSocketINI() {
      if (window.localStorage.getItem("petAssistanceUserId")) {
        // 1、浏览器是否支持WebSocket检测
        if (!("WebSocket" in window)) {
          console.log("您的浏览器不支持WebSocket!");
          return;
        }

        let DEFAULT_URL =
          "ws://" +
          "127.0.0.1:8081" +
          "/websocket/" +
          window.localStorage.getItem("petAssistanceUserId");

        // 3、创建Websocket连接
        const tmpWebsocket = new WebSocket(DEFAULT_URL);

        // 4、全局保存WebSocket操作句柄：main.js 全局引用
        this.$WebSocket.WebsocketINI(tmpWebsocket);

        // 5、WebSocket连接成功提示
        tmpWebsocket.onopen = function (e) {
          console.log("webcoket连接成功");
        };

        //6、连接失败提示
        tmpWebsocket.onclose = function (e) {
          console.log("webcoket连接关闭：", e);
        };
      }
    },
  },
};
</script>

<style lang="less">
#app {
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  position: relative;
  height: 100%;
  width: 100%;
  background-color: hsla(0, 0%, 100%, 0.98);
  overflow: hidden;
}
</style>