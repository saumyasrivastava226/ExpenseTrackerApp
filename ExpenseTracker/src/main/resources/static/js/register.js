/**
 * 
 */
$(function(){
	
	$validator.addMethod('customEmail', function(value,element ){
		return this.optional(element)|| /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value);
		
		
	})
	
	const $registerForm =$('#registerForm');
	
	if($registerForm.length)
	{
		 $registerForm.validate({
			 rules:{
				 
				 name:{
					 required:true,
					 minlength:3
					 
				 },
				 email:{
					 required:true,
					 customEmail:true
					 
				 },
				 password:{
					 required:true,
					 minlength:5,
					 maxlength:15
				 },
				 confirmpassword:{
					  required:true,
					  equalTo:'#password'
				 }
				 
			 },
			 
			 messages: {
				 name:{
					 required:'Please enter the name',
					 minlength:'Name cannot be less than 3 chars'
					 
				 },
				 email:{
					 required:'Please enter email',
					 customEmail:'Please enter a valid email address'
					 
				 },
				 password:{
					 required:'Please set the password',
					 minlength:'Too short',
					 maxlength:'Too long'
				 },
				 confirmpassword:{
					  required:'Please confirm password',
					  equalTo:'Password Mismatch'
				 }
				 
				 
			 }
			 
			 
		 })
		
	}
})