import FormTemplate from '../components/FormTemplate';
import Home from '../components/Home';


const router = [
{
    path: "/home",
    component : Home,
    routes : [
    
    ]
},
{
    path : "/log-in",
    component : FormTemplate,
    style : "centered",
    form : "logIn"
},
{
    path : "/sign-up",
    component : FormTemplate,
    style : "centered", 
    form : "signUp"
}
];

export default router;