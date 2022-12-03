// let cart = new Cart();

let product = new Product();

let user = new User();

let cartItem = new CartItem();

// let orderItems = new OrderItem();

// let Order = new Order();


const loadAllProduct = () => {
    $.ajax({
        "type": "GET",
        url: "http://localhost:8080/api/products/product-view"
    }).done((data) => {
        $.each(data, (i, item) => {
            product = item;
            let str = `
                <div class="col-xs-12 col-sm-6 col-md-4">
                        <div class="product-item">
                            <div class="img">
                                <h6  title="RAW SUDE SHIRT" data-id="${product.id}" id="destcriptionProduct" class="destcriptionProduct"><img width="222,8px" height="222,8px" src="${product.urlImage}" class="img-responsive main-img main" alt="RAW SUDE SHIRT"></h6>
                                <h6  title="RAW SUDE SHIRT" data-id="${product.id}" id="destcriptionProduct" class="destcriptionProduct"  ><img width="222,8px" height="222,8px" src="${product.urlImage}" class="img-responsive sub-img" alt="RAW SUDE SHIRT"></h6>
                            </div>
                            <div class="info">
                                <h4><a href="javascript:void(0);" id="DestcriptionProduct" class="DestcriptionProduct" title="RAW SUDE SHIRT">${product.title}</a>
                                </h4>
                            </div>
                            <div class="box-price">
                                <span class="price">${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(product.price)}</span>
                            </div>
                            <div class="actions text-center">
                                <button id="top-up" style="color:blue;"  data-img="${product.urlImage}" data-price="${product.price}" data-name="${product.title}" data-id="${product.id}" class="btn btn-light btn-top" title="Mua hàng"><b>Mua hàng</b></button>
                            </div>
                        </div>
                    </div>
            `
            $(".products-items").append(str)
        })
        handleCreateCart()
    }).fail(() => {
        App.SweetAlert.showErrorAlert("Customer Errors")
    })
}

const getUserByUserName = () => {
    return $.ajax({
        "type": "GET",
        url: "http://localhost:8080/api/users/" + $("#userNameLogin").text()
    }).done((data) => {
        console.log(data)
        user = data;
    }).fail((e) => {
        console.log(e)
    })
}

const handleCreateCart = () => {
    $(".btn-top").on("click", function () {
        let id = $(this).data("id");
        getUserByUserName().then(function () {
            $.ajax({
                "headers": {
                    "accept": "application/json",
                    "content-type": "application/json"
                },
                "type": "POST",
                "url": "http://localhost:8080/api/carts/" + id + "/1",
                "data": JSON.stringify(user)
            }).done((data) => {
                cartItem = data;
                getAmountOrder(cartItem.id)
                App.IziToast.showSuccessAlert("Thêm vào cart thành công")
                $(".btn-top").off()
                handleCreateCart();
            }).fail((e) => {
                console.log(e)
            })
        })
    })
}

const getAmountOrder = (id) => {
    return $.ajax({
        "type": "GET",
        url: "http://localhost:8080/api/carts/" + id
    }).done((data) => {
        let sum = 0
        $.each(data, (i,item)=>{
            cartItem = item;
            sum += cartItem.quantity
        })
        $(".btn-Cart-item").html("")
        let str =`
        <img class="icon-in-menu" src="https://zunezx.com/public/home/img/w-shop.png" alt="Giỏ hàng"><span style=" color: white;
                                                                    background-color: red;
                                                                    border-radius: 50%;
                                                                    position: relative;
                                                                    padding: 2px 4px 2px 4px;
                                                                    font-size: 10px;
                                                                    left: -5px;
                                                                    bottom: 5px;">${sum}</span>
        `
        $(".btn-Cart-item").append(str)
    }).fail((e) => {
        $(".btn-Cart-item").html("")
        let str =`
        <img class="icon-in-menu" src="https://zunezx.com/public/home/img/w-shop.png" alt="Giỏ hàng"><span style=" color: white;
                                                                    background-color: red;
                                                                    border-radius: 50%;
                                                                    position: relative;
                                                                    padding: 2px 4px 2px 4px;
                                                                    font-size: 10px;
                                                                    left: -5px;
                                                                    bottom: 5px;">1</span>
        `
        $(".btn-Cart-item").append(str)
    })
}

$(() => {
    loadAllProduct();
})