document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');

    fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: username, password: password })
    })
    .then(response => {
        if (response.ok) {
            alert('登録が成功しました。ログインしてください。');
            window.location.href = 'login.html';
        } else {
            return response.text().then(text => { throw new Error(text) });
        }
    })
    .catch(error => {
        errorMessage.textContent = '登録に失敗しました: ' + error.message;
        errorMessage.style.display = 'block';
    });
});
