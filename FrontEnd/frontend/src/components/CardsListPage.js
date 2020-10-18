import React from 'react';
import { Row, Col, Alert } from 'react-bootstrap';
import api from '../app/api';

class CardsListPage extends React.Component {
    constructor (props) {
        super(props);
        this.state = {
            items : []
        };
        this.deleteItem = this.deleteItem.bind(this);
    }

    async deleteItem(index) {
        var items = this.state.items;
        if(index < items.length) {
            items.splice(index, 1);
            this.setState({
                items : items
            });
        }
    }

    async componentDidMount() {
        
        try {
            const response = await api.get(this.props.router.listApi);
            this.setState({
                items : response.data
            });
        } catch(error) {
            console.log(error.response);
        }
    }
    
    render () {
        
        var html;
        
        var rowList = [];
        var tmpList = [];
        var i = 0;
        var j = 0;
        for(j = 0; j !== this.state.items.length; ++j) {
            tmpList.push(this.state.items[j])
            i++;
            if(i === 3 || j + 1 === this.state.items.length) {
                
                rowList.push(
                    <Row key={j}>
                        {tmpList.map((itemCol, index) => {
                            return ( 
                                <Col md="4" key={index}>

                                    <this.props.router.card index={index} deleteHandler={this.deleteItem} item={itemCol}/>
                                </Col>
                            );
                        })}
                    </Row>
                );
                i = 0;
                tmpList = [];
            }
        }
        
        html = (
            <React.Fragment>
                    {this.state.items.length !==0 ?    
                        rowList.map((row) => {
                            return row;
                        })
                        :
                        <Alert variant="secondary" style={{ marginTop : '15px' }}>
                            Sorry no items could be found.
                        </Alert>
                    }
                    
            </React.Fragment>
        )
        return html;
    }

}

export default CardsListPage;