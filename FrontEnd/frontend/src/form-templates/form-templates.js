import TextInput from '../components/inputs/TextInput'
import EmailInput from '../components/inputs/EmailInput'
import PasswordInput from '../components/inputs/PasswordInput'
import PasswordInputWithConf from '../components/inputs/PasswordInputWithConf'
import UsernameInput from '../components/inputs/UsernameInput';

export default class FormTemplates {
    logIn () {
        var form = {
            apiCall : "/api",
            redirect : "/home",
            submitText : "Log In",
            header : "Log In",
            components : [
                {
                    inputName : "Email",
                    inputType : EmailInput
                },
                {
                    inputName : "Password",
                    inputType : PasswordInput
                }
    
            ]
        };

        return form;

    }

        signUp () {
            var form = {
                apiCall : "/api",
                redirect : "/home",
                submitText : "SignUp",
                header : "Sign Up",
                components : [
                    {
                        inputName : "Email",
                        inputType : EmailInput
                    },
                    {
                        inputName : "Username",
                        inputType : UsernameInput
                    },
                    {
                        inputName : "Password",
                        inputType : PasswordInputWithConf
                    },
                    {
                        inputName : "Address",
                        inputType : TextInput
                    },
                    {
                        inputName : "Phone Number",
                        inputType : TextInput
                    }
                ]
            };
        return form;
    }
};