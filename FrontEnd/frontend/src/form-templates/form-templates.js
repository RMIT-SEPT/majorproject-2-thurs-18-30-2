import TextInput from '../components/inputs/TextInput';
import EmailInput from '../components/inputs/EmailInput';
import PasswordInput from '../components/inputs/PasswordInput';
import PasswordInputWithConf from '../components/inputs/PasswordInputWithConf';
import UsernameInput from '../components/inputs/UsernameInput';

export default class FormTemplates {
    logIn () {
        var form = {
            apiCall : "/customer/verify",
            responseHandler : "logIn",
            redirect : "/home",
            submitText : "Log In",
            header : "Log In",
            components : [
                {
                    input : "email",
                    inputName : "Email",
                    inputType : EmailInput
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
            apiCall : "/customer",
            redirect : "/log-in",
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
                // {
                //     input : "username",
                //     inputName : "Username",
                //     inputType : UsernameInput
                // },
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