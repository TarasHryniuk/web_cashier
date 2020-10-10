var self = this;

self.categories = [];
self.products = [];

self.pickedProduct = null;

self.getCategories = function() {
    $.get('http://localhost:8080/final_project_war_exploded/categories', function(data) {
        self.categories = data;

        data.forEach(function(category, i) {
            $('#dropdown-categories-menu').append('<a id="category-' + category.id + '" class="dropdown-item" href="#">' + category.name + '</a>');

            $('#category-' + category.id).on("click", function( event ) {
                $('#dropdown-categories').text(category.name);
                $('input[name="categories.id"]').val(category.id);
                self.getProducts(category.id);
            });
        });
    });
};

self.getProducts = function(categoryId) {
    $.get('http://localhost:8080/final_project_war_exploded/products?category_id=' + categoryId, function(data) {
        self.products = data;

        $('#products tbody').empty();

        data.forEach(function(product, i) {
            var str = '<tr id="product-' + product.id + '">';

            str += '<td>' + product.name + '</td>';
            str += '<td>' + (product.price / 100.0).toFixed(2) + '</td>';
            str += '<td>' + (product.weight / 1000.0).toFixed(3) + ' кг</td>';
            str += '<td>' + product.count + '</td>';

            str += '</tr>';

            $('#products tbody').append(str);

            var $product = $('#product-' + product.id);

            $product.on("click", function( event ) {
                $('#products tr').removeClass('active');
                $product.addClass('active');

                self.pickedProduct = product;
                $('input[name="product_id"]').val(product.id);

                $('#product-count-group').removeClass('hided');
                $('#addProduct').removeClass('hided');
            });
        });

        $('#products').removeClass('hided');
    });
};

self.getCategories();

$('#main-nav .btn-link').removeClass('active');
$('#cashier-window-tab').addClass('active');

$('#addProduct').on("click", function( event ) {
    var count = $('input[name="count"]').val();

    if (count > self.pickedProduct.count) {
        $('#countAlertError').removeClass('hided');
    } else {
        $('#countAlertError').addClass('hided');
        $('input[type="submit"].hided').click();
    }
});
