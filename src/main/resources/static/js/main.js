$(document).ready(function(){
            $('#ingredientTable').on('click', 'button[type="button"]', function(e){
    		   $(this).closest('tr').remove();

    		    e = e || window.event;
        		e = e.target || e.srcElement;

    			$.post("/recipes/removeingredient", {
    				id : e.id
    			}, function(data) {}).fail(function(err, status) {
    				alert("There has been an error - " + error + "; Status - " + status)
    			});
    		});

    		$("#btnAddIngredient").click(function(){
    			var ingredient = $("#ingredientSelection option:selected").val();
    			var ingredientTitle = $("#ingredientSelection option:selected").text();
    			var ingredientMeasurement = $("#ingredientMeasurementInput").val();

    			$("#btnAddIngredient").prop("disabled", true);

    			$.post("/recipes/create/addingredient", {
    				ingredient : ingredient,
    				ingredientMeasurement : ingredientMeasurement
    			}, function(data) {
    				$("#btnAddIngredient").prop("disabled", false);
    				$("#ingredientWithMeasurementTbody").append('<tr> <td>' + ingredientMeasurement + '</td> <td>' + ingredientTitle + '</td> <td><button type="button" class="btn btn-outline-danger" id="deleteIngredient">X</button></td></tr>');
    			}).fail(function(err, status) {
    				alert("There has been an error - " + error + "; Status - " + status)
    			});
            });
});