import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router.js'
import store from './store'


// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css'

const vuetify = createVuetify({
  components,
  directives,
  theme: {
    defaultTheme: 'todo',
    themes: {
      todo: {
        dark: false,
        colors: {
          primary: '#4F6BED',
          secondary: '#7C8BF2',
          success: '#43A047',
          error: '#E53935',
          warning: '#FB8C00',
        },
      },
    },
  },
})

createApp(App)
  .use(router)
  .use(store)
  .use(vuetify)
  .mount('#app')
