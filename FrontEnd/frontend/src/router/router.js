import FormTemplate from '../components/utils/FormTemplate';
import Home from '../components/Home';
import AboutUs from "../components/AboutUs";
import ContactUs from "../components/ContactUs";
import Profile from '../components/Profile';


const router = [
    {
        path: "/home",
        component : Home,
        routes : [
        
        ]
    },
    {
        path: "/about-us",
        component : AboutUs,
        routes : [

        ]
    },
    {
        path: "/contact-us",
        component : ContactUs,
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
    },
    {
        path : "/profile",
        component : Profile
    }
];

export default router;