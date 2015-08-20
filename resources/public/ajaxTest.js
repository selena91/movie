 $.ajax({
   url:"/register-user",
   type:"POST",
   data:{title:c,subtitle:h,content:d},
   cache:!1,
   success:function(){
     console.log("uspesnoo");
   },
   error:function(){
      console.log("greska");
   }//end error
 });//end ajax
