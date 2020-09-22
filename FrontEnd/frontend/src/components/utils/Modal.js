import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Modal as Mod, Button } from 'react-bootstrap';

import { exitModal } from '../../app/reducers/modalSlice';

function Modal() {
    const modal = useSelector(state => state.modal);
    const dispatch = useDispatch();

    function handleClose() {
        dispatch(exitModal());
    }
    return (

        <Mod
            show={modal.show}
            onHide={handleClose}
            backdrop="static"
            keyboard={false}
        >
            <Mod.Header closeButton>
                <Mod.Title>{modal.structure.title}</Mod.Title>
            </Mod.Header>
            <Mod.Body>
                {modal.structure.body}
            </Mod.Body>
            <Mod.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Close
                </Button>
            </Mod.Footer>
        </Mod>
    );
}

export default Modal;