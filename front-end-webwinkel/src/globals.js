let globals = {
  authenticate: function (username, password, cb) {
    let body = JSON.stringify({
      username: username,
      password: password
    });

    let options = {
      method: 'post',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: body
    };


    fetch(environment.API.accountservice + '/authenticate', options).then(data => {

      if (!data.ok) {
        if (data.status === 401 || data.status === 404) {
          alert("Verkeerde gebruikersnaam wachtwoord combinatie");
        } else {
          throw Error(data.statusText);
        }
      }

      return data.text();
    }).then(refreshToken => {
      localStorage.setItem("refreshToken", refreshToken);
      this.refresh(cb);
    }).catch(error => {
      console.log('error', error);
    })
  },

  refresh: function (cb) {
    let refreshToken = localStorage.getItem("refreshToken");

    fetch(environment.API.accountservice + '/refresh', {
      method: 'post',
      headers: {
        'Refresh-Token': refreshToken
      }
    }).then(data => {
      if (data.status === 401 || data.status === 500) {
        this.logout();
      } else {
        return data.text();
      }
    }).then(accessToken => {
      let claims = JSON.parse(atob(accessToken.split('.')[1]));
      if (claims && claims.role === environment.role) {
        localStorage.setItem("accessToken", accessToken);

        if (cb) {
          return cb();
        }
      }
      alert('Het gekozen account is geen \'magazijn medewerker\' account');
      this.logout();
    }).catch(err => {
      console.log('err', err);
      this.logout();
    });
  },

  logout: function () {
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("accessToken");
    location.reload();
  },

  makeAuthenticatedCall: function (url, options = {}) {
    let accessToken = localStorage.getItem('accessToken');
    options.mode = 'cors';

    if (!('headers' in options)) {
      options.headers = {};
    }
    options.headers['Access-Token'] = accessToken;

    return fetch(url, options).then(data => {
      if (data.status === 401 || data.status === 500) {
        this.refresh();
      } else {
        return Promise.resolve(data);
      }
    }).catch(err => {
      console.log('err', err);
      return Promise.reject(err)
    })
  },

};
