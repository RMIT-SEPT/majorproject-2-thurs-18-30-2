import React from 'react';
import { Link } from 'react-router-dom';
import { List, ListItem, ListItemText, ListItemIcon } from '@material-ui/core';
import '../../css/SideBar.css';

function SideBar({items}) {
       
        
    return (
        <div className="sidebar">
            <List disablePadding dense>
                {items.map(function({ label, name, path, icon, ...rest }) {
                    var TagName = icon;
                    return (
                    <ListItem key={name} button {...rest} component={Link} to={path}>
                        <ListItemIcon>
                            <TagName fontSize="large" style={{ color: "white" }}/>
                        </ListItemIcon>
                        <ListItemText>{label}</ListItemText>
                    </ListItem>
                )})}
            </List>
        </div>
    );
}

export default SideBar;