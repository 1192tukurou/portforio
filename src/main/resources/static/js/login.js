document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');

//    fetch('http://localhost:8080/api/auth/login', {
//        method: 'POST',
//        headers: { 'Content-Type': 'application/json' },
//        body: JSON.stringify({ username: username, password: password })
//    })
//    .then(response => {
//        if (response.ok) {
//            alert('ログイン成功！');
//            window.location.href = 'main.html';
//        } else {
//            return response.text().then(text => { throw new Error(text) });
//        }
//    })
//    .catch(error => {
//        errorMessage.textContent = 'ログインに失敗しました: ' + error.message;
//        errorMessage.style.display = 'block';
//    });
});
