const clientId = 'pkcClient';
const redirectUri = 'http://localhost:5500/';
const authServer = 'http://127.0.0.1:9090';
const scope = 'openid';
let codeVerifier = null;
const loginForm = document.getElementById("loginForm");
const loggedInCard = document.getElementById("loggedInCard");
const booksContainer = document.getElementById("booksContainer");
const bookInfo = document.getElementById("bookInfo");
const formulario = document.getElementById("miFormulario");

// Paso 1: Inicia el login
async function login() {
  if (localStorage.getItem("access_token") == null) {
    codeVerifier = generateRandomString(64);
    const codeChallenge = await generateCodeChallenge(codeVerifier);
    localStorage.setItem("code_verifier", codeVerifier);
    
    const params = new URLSearchParams({
      response_type: 'code',
      client_id: clientId,
      state:'some-state-crsfasdasda',
      scope: scope,
      redirect_uri:redirectUri ,
      code_challenge: codeChallenge,
      code_challenge_method: 'S256'
    });
      console.log(`${authServer}/authorize?${params.toString()}`)
      window.location = `${authServer}/oauth2/authorize?${params.toString()}`;
  } else {
    alert('access token present')
  }
}

// Verifica si volvió con `code`
window.onload = async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const code = urlParams.get('code');
  const codeVerifier = localStorage.getItem("code_verifier");
  if (code && codeVerifier !== null) {
    loginForm.classList.add('d-none')
    //consulto por el token
    const body = new URLSearchParams({
      grant_type: 'authorization_code',
      client_id: clientId,
      code: code,
      redirect_uri: redirectUri,
      code_verifier: codeVerifier
    });
    
    fetch("http://127.0.0.1:9090/oauth2/token", {
      method: 'POST',
      headers: { "Accept": "*/*",'Content-Type': 'application/x-www-form-urlencoded' },
      body: body.toString()
    }).then(response => {
      if (!response.ok) {
        throw new Error("Error en la respuesta de la API");
      }
      
      loginForm.classList.add("d-none")
      loggedInCard.classList.remove("d-none")
      fetchAll()
      return response.json();
    }).then(data => {
        console.log(data)
        localStorage.setItem("access_token", data.access_token);
    }).catch(error => {
        console.error("Hubo un problema con la llamada a la API:", error);
        alert("Error al llamar la API: " + error.message);
    });
  }else{

  }
};

//traigo los los titulos
function fetchAll() {
  fetch("http://127.0.0.1:8081/api/book/", {
    method: 'GET'
    //,headers: {'Authorization':'Bearer: '.concat(localStorage.getItem('access_token'))}
  }).then(response => {
    if (!response.ok) {
      throw new Error("Error en la respuesta de la API");
    }
    return response.json(); // O .text() según lo que devuelva tu API
  }).then(data => {
    return data.forEach(book => {
      const card = document.createElement("div");
      card.id = "card-".concat(book)
      card.className = "col-md-4 btn";
      card.onclick = ()=>getBookInfo(book)
      card.innerHTML = `
          <div class="card h-100 shadow-sm">
              <div class="card-body">
                  <h5 class="card-title">${book} libro</h5>
                <button class="btn btn-outline-dark"> obtener info ${book}</button>
              </div>
          </div>
      `;
      booksContainer.appendChild(card);
  });
  })
}

//traigo la info del libro por titulo
function getBookInfo(book){
  fetch("http://127.0.0.1:8081/api/book/".concat(book), {
    method: 'GET',
    headers: { "Accept": "*/*","Authorization":"Bearer ".concat(localStorage.getItem('access_token'))},
    
  }).then(response => {
    if (response.status === 403) {
      throw new Error("User not authorized");
    }
    return response.json(); // O .text() según lo que devuelva tu API
  })
    .then(data => {
      bookInfo.classList.remove('d-none')
      const cardInfo = document.createElement("div")
        cardInfo.innerHTML = `
          <div class="card h-100 shadow-sm m-1">
              <div class="card-body border rounded d-flex flex-column p-2">
                  <h2 class="d-flex justify-content-center">Title: ${data.title}</h2>
                  <p class="d-flex justify-content-center">Category: ${data.category}</p>
                  <p class="d-flex justify-content-center">Author: ${data.author}</p>
              </div>
          </div>
      `;
      bookInfo.appendChild(cardInfo)
    })
    .catch(error => {
      console.error("Hubo un problema con la llamada a la API:", error);
      alert("Error al llamar la API: " + error.message);
    });
}

//agregar un libro
function addBook(){
    const titleBook = document.getElementById("titleBook").value;
    const authorBook = document.getElementById("authorBook").value;
    const categoryBook = document.getElementById("categoryBook").value;
    

    const datos = {
      title: titleBook,
      author: authorBook,
      category: categoryBook
    };

    fetch("http://127.0.0.1:8081/api/book/", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization":"Bearer ".concat(localStorage.getItem('access_token'))
      },
      body: JSON.stringify(datos)
    }).then(response => {
      if (response.status === 403) {
        console.log(response)
        alert('User not authorized')
      }
      return response.json(); // o .text() si la respuesta no es JSON
    })
    .then(data => {
      console.log("Respuesta del servidor:", data);
      alert("Libro agregado correctamente");
    })
}

// Utils PKCE
function generateRandomString(length) {
  const charset = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~';
  let result = '';
  const array = new Uint8Array(length);
  window.crypto.getRandomValues(array);
  for (let i = 0; i < array.length; i++) {
    result += charset[array[i] % charset.length];
  }
  return result;
}
async function generateCodeChallenge(verifier) {
  const encoder = new TextEncoder();
  const data = encoder.encode(verifier);
  const digest = await window.crypto.subtle.digest('SHA-256', data);
 
  return btoa(String.fromCharCode(...new Uint8Array(digest)))
    .replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
    
}
function logout(){
  localStorage.clear()
  loginForm.classList.remove("d-none")
  loggedInCard.classList.add("d-none")
}