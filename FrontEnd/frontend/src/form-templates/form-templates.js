import TextInput from '../components/inputs/TextInput';
import EmailInput from '../components/inputs/EmailInput';
import PasswordInput from '../components/inputs/PasswordInput';
import PasswordInputWithConf from '../components/inputs/PasswordInputWithConf';
import UsernameInput from '../components/inputs/UsernameInput';

export default class FormTemplates {
    logIn () {
        var form = {
            apiCall : "/user/login",
            responseHandler : "logIn",
            redirect : "/home",
            submitText : "Log In",
            header : "Log In",
            components : [
                {
                    input : "username",
                    inputName : "Username",
                    inputType : TextInput
                },
                {
                    input : "password",
                    inputName : "Password",
                    inputType : PasswordInput
                }
    
            ]
        };

        return form;

    }

    signUp () {
        var form = {
            apiCall : "/customer/register",
            redirect : "/log-in",
            modal : {
                title : "Successfull registration",
                body : "Thanks for choosing us, you have successfully registered"
            },
            submitText : "SignUp",
            header : "Sign Up",
            components : [
                {
                    input : "fName",
                    inputName : "First Name",
                    inputType : TextInput
                },
                {
                    input : "lName",
                    inputName : "Last Name",
                    inputType : TextInput
                },
                {
                    input : "email",
                    inputName : "Email",
                    inputType : EmailInput
                },
                {
                    input : "username",
                    inputName : "Username",
                    inputType : UsernameInput
                },
                {
                    input : "password",
                    inputName : "Password",
                    inputType : PasswordInputWithConf
                },
                {
                    input : "address",
                    inputName : "Address",
                    inputType : TextInput
                },
                {
                    input : "pNumber",
                    inputName : "Phone Number",
                    inputType : TextInput
                }
            ]
        };
        return form;
    }

    editProfile () {
        var form = {
            apiCall : "/customer/editCustomer",
            redirect : "/profile",
            submitText : "Save",
            header : "Edit Profile",
            components : [
                {
                    input : "fName",
                    inputName : "First Name",
                    inputType : TextInput
                },
                {
                    input : "lName",
                    inputName : "Last Name",
                    inputType : TextInput
                },
                {
                    input : "email",
                    inputName : "Email",
                    inputType : EmailInput
                },
                {
                    input : "username",
                    inputName : "Username",
                    inputType : UsernameInput
                },
                {
                    input : "password",
                    inputName : "Password",
                    inputType : PasswordInputWithConf
                },
                {
                    input : "address",
                    inputName : "Address",
                    inputType : TextInput
                },
                {
                    input : "pNumber",
                    inputName : "Phone Number",
                    inputType : TextInput
                }
            ]
        };
        return form;
    }
};