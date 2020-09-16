import { ContactSupportRounded, 
    InfoRounded,
    AccountCircleRounded } from '@material-ui/icons';

export default class SideBarMenu {
    employeeMenu() {
        var items = [
            { 
                name : 'profile', 
                label : 'Profile',
                path : '/profile',
                icon : AccountCircleRounded
            }
        ];
        items.push(...this.guestMenu());
        return items;    
    }

    customerMenu() {
        var items = [
            { 
                name : 'profile', 
                label : 'Profile',
                path : '/profile',
                icon : AccountCircleRounded
            }
        ];
        items.push(...this.guestMenu());
        return items;
    }

    ownerMenu() {
        var items = [
            { 
                name : 'profile', 
                label : 'Profile',
                path : '/profile',
                icon : AccountCircleRounded
            }
        ];
        items.push(...this.guestMenu());
        return items;
    }

    guestMenu() {
        return [
            { 
                name : 'contactUs', 
                label : 'Contact Us',
                path : '/contact-us',
                icon : ContactSupportRounded
            },
            {
                name : 'aboutUs',
                label : 'About Us',
                path : '/about-us',
                icon :  InfoRounded
            }
        ]
    }
}