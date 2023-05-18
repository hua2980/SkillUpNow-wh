function getCookie(name) {
  const value = '; ' + document.cookie;
  const parts = value.split('; ' + name + '=');
  if (parts.length === 2) return decodeURIComponent(parts.pop().split(';').shift());
}

axios.interceptors.request.use((config) => {
  if (config.url.endsWith('/login') || config.url.endsWith('/signup')) {
    return config;
  }
  const token = getCookie('token');
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + token;
  } else {
    console.log('JWT token not found in the cookie.');
    window.location.href = '/skillupnow/login.html';
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});