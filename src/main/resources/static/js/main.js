document.addEventListener('DOMContentLoaded', function() {
    const productList = document.getElementById('productList');
    const prevPageBtn = document.getElementById('prevPage');
    const nextPageBtn = document.getElementById('nextPage');
    const currentPageSpan = document.getElementById('currentPage');
    const totalPagesSpan = document.getElementById('totalPages');

    let currentPage = 0; // 0-indexed page number
    const pageSize = 6; // Items per page

    function fetchProducts(page) {
        fetch(`http://localhost:8080/api/products?page=${page}&size=${pageSize}`)
            .then(response => response.json())
            .then(data => {
                const products = data.content;
                const totalPages = data.totalPages;

                productList.innerHTML = ''; // Clear current products
                products.forEach(product => {
                    const productCard = document.createElement('div');
                    productCard.className = 'product-card';
                    productCard.innerHTML = `
                        <img src="http://localhost:8080${product.imageUrl || '/images/default.png'}" alt="${product.name}">
                        <h3>${product.name}</h3>
                        <p>価格: ${product.price}円</p>
                        <button class="view-detail-btn" data-id="${product.id}">
                            ${product.sold ? '売り切れ' : '詳細を見る'}
                        </button>
                    `;
                    productList.appendChild(productCard);
                });

                currentPageSpan.textContent = data.number + 1;
                totalPagesSpan.textContent = totalPages;

                prevPageBtn.disabled = data.first;
                nextPageBtn.disabled = data.last;
            })
            .catch(error => {
                console.error('商品一覧の取得中にエラーが発生しました:', error);
                productList.innerHTML = '<p>商品の読み込みに失敗しました。</p>';
            });
    }

    fetchProducts(currentPage);

    prevPageBtn.addEventListener('click', function() {
        if (currentPage > 0) {
            currentPage--;
            fetchProducts(currentPage);
        }
    });

    nextPageBtn.addEventListener('click', function() {
        if (currentPage < parseInt(totalPagesSpan.textContent) - 1) {
            currentPage++;
            fetchProducts(currentPage);
        }
    });

    // 詳細を見るボタンのイベントリスナー
    productList.addEventListener('click', function(event) {
        if (event.target.classList.contains('view-detail-btn')) {
            const productId = event.target.dataset.id;
            window.location.href = `purchase.html?id=${productId}`;
        }
    });
});
