import TextInput from '../components/inputs/TextInput'
import EmailInput from '../components/inputs/EmailInput'
import PasswordInput from '../components/inputs/PasswordInput'

export default class FormTemplates {
    logIn () {
        var form = {
            apiCall : "/api",
            redirect : "/home",
            submitText : "Log In",
            header : "Log In",
            components : [
                {
                    inputName : "UserName",
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
};