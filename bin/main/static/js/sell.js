document.getElementById('sellForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData();
    formData.append('name', document.getElementById('name').value);
    formData.append('price', document.getElementById('price').value);
    formData.append('image', document.getElementById('image').files[0]);

    fetch('http://localhost:8080/api/products', {
        method: 'POST',
        body: formData // FormDataをそのまま送信
    })
    .then(response => {
        if (response.ok) {
            alert('商品を出品しました！');
            window.location.href = 'main.html';
        } else {
            alert('出品に失敗しました。');
        }
    })
    .catch(error => {
        console.error('出品処理中にエラーが発生しました:', error);
        alert('出品処理中にエラーが発生しました。');
    });
});