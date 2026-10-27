<template>
  <div id="app">
    <div class="welcome-message" v-if="isLoggedIn">
      <p class="welcome-text">안녕하세요, <span class="user-name">{{ user.name }}</span>님!</p>
      <p class="description">오늘의 할일은 무엇일까요?</p>
    </div>
    <div class="brand" v-if="isLoggedIn">
      <v-icon class="brand-icon">mdi-checkbox-marked-circle-outline</v-icon> TODO
    </div>
    <NavBar v-if="isLoggedIn"></NavBar>
    <div class="router-view-wrapper">
      <router-view></router-view>
    </div>
    <footer class="footer" v-if="isLoggedIn">
      <p>&copy; 2023 Todo. All rights reserved.</p>
    </footer>
    <BasicPopup :message="popup.message" :status="popup.status" :visible="popup.visible" @close="closePopup" />
  </div>
</template>

<script>
import BasicPopup from "@/components/BasicPopup.vue";
import NavBar from '@/components/NavBar.vue';
import { mapState } from "vuex";

export default {
  name: "App",
  components: {
    NavBar,
    BasicPopup,
  },
  computed: {
    isLoggedIn() {
      return !!this.$store.state.user;
    },
    ...mapState(["popup", "user"])
  },
  methods: {
    closePopup() {
      this.$store.commit("setPopup", {
        ...this.popup,
        visible: false,
      });
    },
  },
};
</script>

<style>
:root {
  --todo-primary: #4F6BED;
  --todo-primary-dark: #3448C5;
  --todo-primary-light: #EEF1FF;
}

html,
body {
  height: 100%;
  margin: 0;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: relative;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.router-view-wrapper {
  flex-grow: 1;
  display: flex;
  margin: auto;
  width: 100%;
  justify-content: center;
}

.brand {
  font-size: 2.5em;
  font-weight: bold;
  color: var(--todo-primary);
  padding-top: 30px;
  padding-bottom: 20px;
  letter-spacing: 0.1em;
}

.brand-icon {
  font-size: 0.9em;
  vertical-align: -0.05em;
}

.container {
  max-width: 1200px;
  margin: 40px auto;
}

.welcome-message {
  margin-bottom: 1rem;
  position: absolute;
  top: 0px;
  right: 20px;
  text-align: right;
}

@media (max-width: 768px) {
  .welcome-message {
    display: none;
  }
}

.welcome-text {
  font-size: 0.9rem;
  font-weight: bold;
}

.user-name {
  color: var(--todo-primary);
}

.description {
  font-size: 0.8rem;
  color: #555;
}

.footer {
  background-color: var(--todo-primary);
  color: white;
  padding: 1rem;
  font-size: 0.9rem;
  text-align: center;
  bottom: 0;
  width: 100%;
}
</style>
