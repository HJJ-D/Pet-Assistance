<template>
  <div class="personal">
    <div class="container">

      <!-- 个人资料 -->
      <div class="imfo">
        <!-- 头像 -->
        <div class="imfo_one">
          <img :src="imfo.userPhotoUrl"
               style="height: 100%; width: auto; border-radius: 50%; border: 1px solid azure;" />
        </div>
        <!-- 信息 -->
        <div class="imfo_two">
          <div class="imfo_two_username">{{ imfo.userAppName }}</div>

          <div class="imfo_two_id"> IP属地：浙江 </div>

          <div class="imfo_two_introduction">个性签名：{{ imfo.userIntroduction }}</div>

          <div class="imfo_two_tag">
            <el-tag v-if="imfo.userGender == 0"><i class="el-icon-female"></i></el-tag>
            <el-tag v-if="imfo.userGender == 1"
                    type="danger"><i class="el-icon-male"></i></el-tag>
          </div>
        </div>
      </div>

      <!-- 收藏/点赞 -->
      <div class="note_and_star">
        <div class="changeShow">
          <el-menu default-active="1"
                   mode="horizontal">
            <el-menu-item index="1"
                          @click="changeShow('点赞')">点赞</el-menu-item>
            <el-menu-item index="2"
                          @click="changeShow('收藏')">收藏</el-menu-item>
          </el-menu>
        </div>

        <!-- 核心内容 -->
        <div class="waterful">
          <div class="watch_item"
               v-for="item in show_data">
            <img :src="item.note.surfacePicture"
                 alt=""
                 srcset=""
                 width="175px"
                 height="auto">

            <div class="watch_item_middle"><span>{{ item.note.title }}</span></div>

            <div class="watch_item_bottom">
              <div>
                <el-avatar :src="item.user.headphoto"
                           size="small"> user </el-avatar>
              </div>
              <div>
                <span class="name">{{ item.user.username }} </span>
              </div>
              <div>
                <i class="el-icon-star-on"></i>{{item.user.praisenum}}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 抽屉--修改个人资料 -->
    <el-drawer title="修改个人资料"
               :visible.sync="drawer"
               :with-header="true"
               append-to-body>

      <h1 style="text-align: center">修改个人资料</h1>

      <div style="padding: 20px; margin-top: 60px;">

        <div style="text-align: center;">
          <el-upload class="avatar-uploader"
                     action=""
                     :http-request="upload"
                     :show-file-list="false"
                     :before-upload="beforeAvatarUpload">
            <img v-if="alter_imfo.userPhotoUrl"
                 :src="alter_imfo.userPhotoUrl"
                 class="avatar">
            <i v-else
               class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>

        <div style="margin-bottom: 15px;">
          <el-input placeholder="请输入内容"
                    v-model="alter_imfo.userAppName">
            <template slot="prepend">用户名</template>
          </el-input>
        </div>

        <div style="margin-bottom: 15px;">
          <el-input placeholder="请输入内容"
                    v-model="alter_imfo.userIntroduction">
            <template slot="prepend">个人简介</template>
          </el-input>
        </div>

        <div style="margin-bottom: 15px;">
          <el-input placeholder="请输入内容"
                    v-model="alter_imfo.userPwd">
            <template slot="prepend">密码</template>
          </el-input>
        </div>

        <div style="margin-bottom: 15px;">
          <el-cascader :options="options"
                       size="medium"
                       v-model="equipmentdistrict">
          </el-cascader>
        </div>

        <el-button type="primary"
                   plain
                   @click="send_alter">提交修改</el-button>
      </div>
    </el-drawer>

    <!-- 抽屉按钮 -->
    <div class="fixed_box">
      <el-button type="primary"
                 plain
                 @click="openDrawer"
                 style="margin-bottom: 4px;">修改资料</el-button>
      <br>
      <el-tooltip class="item"
                  effect="dark"
                  content="哦哦，显然正确入口是修改资料按钮"
                  placement="bottom-start">
        <el-button type="danger"
                   plain>修改头像</el-button>
      </el-tooltip>
    </div>
  </div>
</template>

<script>
import { regionData, codeToText } from "element-china-area-data";
export default {
  data() {
    return {
      equipmentdistrict: [],
      options: regionData,
      show_data: [],
      imfo: {
        petAssistanceUserId: "",
        userAppName: "",
        userPhotoUrl: "",
        userPhoneNum: "",
        userProvince: "",
        userCity: "",
        userArea: "",
        userGender: "",
        userIntroduction: "",
        userPwd: null,
      },
      alter_imfo: {
        petAssistanceUserId: "",
        userAppName: "",
        userPhotoUrl: "",
        userPhoneNum: "",
        userProvince: "",
        userCity: "",
        userArea: "",
        userGender: "",
        userIntroduction: "",
        userPwd: null,
      },
      // 抽屉控制
      drawer: false,
    };
  },
  mounted() {
    // 获取个人资料
    this.get_userimfo();
  },
  methods: {
    changeShow(type_str) {
      // 点赞 收藏
      if ("点赞" == type_str) {
        // http://localhost:8080/userInfo/praise
        this.$http({
          url: "/userInfo/praise",
          method: "get",
        })
          .then((res) => {
            this.show_data = res.data.data;
          })
          .catch((res) => {
            console.log(res);
          });
      } else if ("收藏" == type_str) {
        // http://localhost:8080/userInfo/collection
        this.$http({
          url: "/userInfo/collection",
          method: "get",
        })
          .then((res) => {
            this.show_data = res.data.data;
          })
          .catch((res) => {
            console.log(res);
          });
      }
    },
    get_userimfo() {
      this.imfo.petAssistanceUserId = window.localStorage.getItem(
        "petAssistanceUserId"
      );
      this.imfo.userAppName = window.localStorage.getItem("userAppName");
      this.imfo.userPhoneNum = window.localStorage.getItem("userPhoneNum");
      this.imfo.userPhotoUrl = window.localStorage.getItem("userPhotoUrl");
      this.imfo.userProvince = window.localStorage.getItem("userProvince");
      this.imfo.userCity = window.localStorage.getItem("userCity");
      this.imfo.userArea = window.localStorage.getItem("userArea");
      this.imfo.userGender = window.localStorage.getItem("userGender");
      this.imfo.userIntroduction =
        window.localStorage.getItem("userIntroduction");
    },

    // 上传头像
    upload(param) {
      const formdate = new FormData();
      formdate.append("file", param.file);
      formdate.append(
        "uid",
        window.localStorage.getItem("petAssistanceUserId")
      );
      const url = "/user/headPhotoUpload";
      const headers = {
        "Content-Type": "multipart/form-data",
      };
      // axios.post的构造函数有顺序，不可乱
      this.$http
        .post(url, formdate, headers)
        .then((res) => {
          // console.log(data)
          this.$message.success("图片上传成功");
          this.alter_imfo.userPhotoUrl = res.data.data;
          window.localStorage.setItem("userPhotoUrl", res.data.data);
        })
        .catch((response) => {
          console.log(response);
        });
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },

    // 打开抽屉
    openDrawer() {
      this.alter_imfo.userAppName = this.imfo.userAppName;
      this.alter_imfo.petAssistanceUserId = this.imfo.petAssistanceUserId;
      this.alter_imfo.userArea = this.imfo.userArea;
      this.alter_imfo.userCity = this.imfo.userCity;
      this.alter_imfo.userGender = this.imfo.userGender;
      this.alter_imfo.userPhoneNum = this.imfo.userPhoneNum;
      this.alter_imfo.userProvince = this.imfo.userProvince;
      this.alter_imfo.userIntroduction = this.imfo.userIntroduction;
      this.alter_imfo.userPhotoUrl = this.imfo.userPhotoUrl;
      this.equipmentdistrict.push(this.imfo.userProvince);
      this.equipmentdistrict.push(this.imfo.userCity);
      this.equipmentdistrict.push(this.imfo.userArea);
      this.drawer = true;
    },
    // 发起修改请求
    send_alter() {
      this.alter_imfo.userProvince = this.equipmentdistrict[0];
      this.alter_imfo.userCity = this.equipmentdistrict[1];
      this.alter_imfo.userArea = this.equipmentdistrict[2];
      console.log(this.alter_imfo);
      this.$http({
        url: "/user/update",
        method: "put",
        data: this.alter_imfo,
      })
        .then((res) => {
          this.$message.success(res.data.data);
          this.update_storage();
          this.get_userimfo();
          this.drawer = false;
        })
        .catch((res) => {
          console.log(res);
        });
    },
    update_storage() {
      window.localStorage.setItem(
        "petAssistanceUserId",
        this.alter_imfo.petAssistanceUserId
      );
      window.localStorage.setItem("userAppName", this.alter_imfo.userAppName);
      window.localStorage.setItem("userPhoneNum", this.alter_imfo.userPhoneNum);
      window.localStorage.setItem("userPhotoUrl", this.alter_imfo.userPhotoUrl);
      window.localStorage.setItem("userProvince", this.alter_imfo.userProvince);
      window.localStorage.setItem("userCity", this.alter_imfo.userCity);
      window.localStorage.setItem("userArea", this.alter_imfo.userArea);
      window.localStorage.setItem("userGender", this.alter_imfo.userGender);
      window.localStorage.setItem(
        "userIntroduction",
        this.alter_imfo.userIntroduction
      );
    },
  },
};
</script>

<style lang="less" scoped>
.personal {
  width: 100%;
  height: 100%;
  position: relative;
  // overflow: scroll;
}
// .personal:-webkit-scrollbar {
//     width: 0;
//     height: 0;
//     background-color: transparent;
// }

.container {
  width: 60%;
  height: 100%;
  // background-color: bisque;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  overflow: scroll;
}

.container::-webkit-scrollbar {
  width: 0;
  height: 0;
  background-color: transparent;
}

.imfo {
  width: 100%;
  height: 30%;
  // border: 1px solid black;
  .imfo_one {
    width: 40%;
    height: 100%;
    // background-color: cadetblue;
    float: left;
    padding-left: 40px;
    box-sizing: border-box;
  }
  .imfo_two {
    width: 60%;
    height: 100%;
    // background-color: burlywood;
    float: left;
    padding-left: 20px;
    box-sizing: border-box;
    .imfo_two_username {
      padding-top: 10px;
      font-weight: 600;
      font-size: 27px;
      color: #333;
    }
    .imfo_two_id {
      padding-top: 5px;
      font-size: 12px;
      color: rgba(51, 51, 51, 0.6);
    }
    .imfo_two_introduction {
      padding-top: 14px;
      font-size: 18px;
      color: #333;
    }
    .imfo_two_tag {
      padding-top: 10px;
      .el-tag.el-tag--light {
        border-radius: 50%;
      }
    }
    .imfo_two_others {
      padding-top: 10px;
      font-weight: 400;
      font-size: 12px;
      color: #333;
    }
  }
}
.el-cascader-node {
  max-width: 1000px;
}
.note_and_star {
  margin-top: 10px;
  width: 100%;
  position: relative;
}

.changeShow {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
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
  // 去除下划线，文本装饰线
  .el-menu--horizontal > .el-menu-item.is-active {
    border-bottom: none;
    text-decoration: none;
  }
  .el-menu.el-menu--horizontal {
    border-bottom: none;
  }
  .el-menu--horizontal.el-menu {
    border-bottom: none;
  }
}
// 瀑布流布局
.waterful {
  margin-top: 20px;
  padding-left: 10px;
  column-count: 3; //想要排成的列数
  column-gap: 0; // 列间距
  margin-bottom: 150px;
  position: absolute;
  top: 30px;
  left: 50%;
  transform: translateX(-50%);
}

.watch_item {
  break-inside: avoid; //避免“瀑布流”内部的内容被断开
  width: 175px;
  margin-bottom: 12px;
  margin-right: 4px;
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

.fixed_box {
  position: fixed;
  top: 30px;
  right: 30px;
}
</style>