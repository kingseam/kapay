$(function () {
  console.log("ready!");

  $('#get').on('click', function () {
    $.ajax({
      type: "get",
      url: "/todos/" + $("#url").val(),
      contentType: 'application/json',
      success: function (data) {
        console.log(data);
        $("#resultJson").text(JSON.stringify(data));
      },
      error: function (reqeust, status, error) {
        $("#resultJson").text(JSON.stringify(reqeust));
      }
    });
  });

  $('#post').on('click', function () {
    $.ajax({
      type: "post",
      url: "/todos/" + $("#url").val(),
      dataType: 'json',
      contentType: 'application/json',
      data: $("#inputJson").val(),
      success: function (data) {
        console.log(data);
        $("#resultJson").text(JSON.stringify(data));
      },
      error: function (reqeust, status, error) {
        console.log(reqeust);
        $("#resultJson").text(JSON.stringify(reqeust));
      }
    });
  });

  $('#put').on('click', function () {
    $.ajax({
      type: "put",
      url: "/todos/" + $("#url").val(),
      dataType: 'json',
      contentType: 'application/json',
      data: $("#inputJson").val(),
      success: function (data) {
        console.log(data);
        $("#resultJson").text(JSON.stringify(data));
      },
      error: function (reqeust, status, error) {
        $("#resultJson").text(JSON.stringify(reqeust));
      }
    });
  });
});