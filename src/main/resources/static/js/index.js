$(function() {
  console.log( "ready!" );

  $('#get').on('click',function(){
    $.ajax({
      type : "get",
      url : "/todos/"+$("#url").val(),
      contentType : 'application/json',
      success : function(data){
        console.log(data);
        $("#resultJson").text(JSON.stringify(data));
      },
      error   : function(reqeust, status, error){
        console.log(reqeust);
        console.log(status);
        console.log(error);
      }
    });
  });

  $('#post').on('click',function(){
    $.ajax({
      type : "post",
      url : "/todos/"+$("#url").val(),
      dataType : 'json',
      contentType : 'application/json',
      data : $("#inputJson").val(),
      success : function(data){
        console.log(data);
        $("#resultJson").text(data);
      },
      error   : function(reqeust, status, error){
        console.log(reqeust);
        console.log(status);
        console.log(error);
      }
    });
  });

  $('#put').on('click',function(){
    $.ajax({
      type : "put",
      url : "/todos/"+$("#url").val(),
      dataType : 'json',
      contentType : 'application/json',
      data : $("#inputJson").val(),
      success : function(data){
        console.log(data);
        $("#resultJson").text(data);
      },
      error   : function(reqeust, status, error){
        console.log(reqeust);
        console.log(status);
        console.log(error);
      }
    });
  });
});