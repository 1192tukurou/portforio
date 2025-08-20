document.addEventListener('DOMContentLoaded', function() {
    const productDetailDiv = document.getElementById('productDetail');
    const confirmPurchaseBtn = document.getElementById('confirmPurchaseBtn');
    const errorMessage = document.getElementById('errorMessage');

    // URLから商品IDを取得
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    let currentProduct = null; // 現在表示している商品情報を保持

    if (productId) {
        // 商品IDを使ってバックエンドから商品詳細を取得
        fetch(`http://localhost:8080/api/products/${productId}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('商品が見つかりませんでした。');
                }
            })
            .then(product => {
                currentProduct = product;
                // 商品詳細を表示
                productDetailDiv.innerHTML = `
                    <img src="http://localhost:8080${product.imageUrl || '/images/default.png'}" alt="${product.name}">
                    <h2>${product.name}</h2>
                    <p>価格: ${product.price}円</p>
                    <p>${product.sold ? 'この商品は売り切れです。' : ''}</p>
                `;
                // 売り切れの場合は購入ボタンを無効化
                if (product.sold) {
                    confirmPurchaseBtn.disabled = true;
                    confirmPurchaseBtn.textContent = '売り切れ';
                }
            })
            .catch(error => {
                errorMessage.textContent = error.message;
                errorMessage.style.display = 'block';
                confirmPurchaseBtn.disabled = true; // エラー時は購入ボタンを無効化
            });
    } else {
        errorMessage.textContent = '商品IDが指定されていません。';
        errorMessage.style.display = 'block';
        confirmPurchaseBtn.disabled = true;
    }

    // 購入確定ボタンのイベントリスナー
    confirmPurchaseBtn.addEventListener('click', function() {
        if (currentProduct && !currentProduct.sold) {
            if (confirm('本当にこの商品を購入しますか？')) {
                fetch(`http://localhost:8080/api/products/${currentProduct.id}/purchase`, {
                    method: 'PUT'
                })
                .then(response => {
                    if (response.ok) {
                        alert('購入しました！');
                        window.location.href = 'main.html'; // 商品一覧に戻る
                    } else {
                        return response.text().then(text => { throw new Error(text) });
                    }
                })
                .catch(error => {
                    errorMessage.textContent = '購入に失敗しました: ' + error.message;
                    errorMessage.style.display = 'block';
                });
            }
        }
    });
});
