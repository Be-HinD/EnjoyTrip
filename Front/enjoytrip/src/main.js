
import { createApp } from "vue";
import { createPinia } from "pinia";
import VCalendar from "v-calendar";
import "v-calendar/style.css";
import App from "./App.vue";
import router from "./router";
import BootstrapVue3 from "bootstrap-vue-3";
import "./index.css";

import './assets/main.css';




//BootStrap 사용
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue-3/dist/bootstrap-vue-3.css";

import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
const app = createApp(App);

app.use(createPinia().use(piniaPluginPersistedstate));
app.use(router);
app.use(BootstrapVue3);
app.use(VCalendar, {});
app.mount("#app");
