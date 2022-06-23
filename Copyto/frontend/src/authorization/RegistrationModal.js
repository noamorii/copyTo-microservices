import React, {useState} from "react";
import {Button, Form, Modal} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.css'

export const RegistrationModal = () => {
    const [show, setShow] = useState(false);
    const [validated, setValidated] = useState(false);
    // const [surname, setSurname] = useState('');
    // const [email, setEmail] = useState('');
    // const [number, setNumber] = useState('');
    // const [password, setPassword] = useState('');
    // const [copywriter, setCopywriter] = useState(false);
    // const [client, setClient] = useState(false);
    //
    // const [error, setError] = useState('')

    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);
    const handleSubmit = (e) => {
        const form = e.currentTarget;
        if (form.checkValidity() === false) {
            e.preventDefault();
            e.stopPropagation();
        }

        setValidated(true);
    }

    // const validation = (e) => {
    //     e.preventDefault();
    //     let valid = true;
    //
    //     if (firstname.trim().length === 0 ||
    //         surname.trim().length === 0 ||
    //         email.trim().length === 0 ||
    //         number.trim().length === 0 ||
    //         password.trim().length === 0
    //     ) {
    //         setError('Forms cannot be empty');
    //         valid = false;
    //         e.preventDefault();
    //     } else {
    //         const regex = /[^a-zA-Z]/;
    //         if (firstname.trim().length >= 3) {
    //             if (firstname.match(regex)) {
    //                 setError('Incorrect firstname format');
    //                 valid = false;
    //                 e.preventDefault();
    //                 return;
    //             }
    //         } else {
    //             setError('Firstname is too short')
    //         }
    //         if (surname.trim().length >= 3) {
    //             if (surname.match(regex)) {
    //                 setError('Incorrect surname format');
    //                 valid = false;
    //                 e.preventDefault();
    //                 return;
    //             }
    //         } else {
    //             setError('Surname is too short')
    //         }
    //         if (!email.match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/)) {
    //             setError('Incorrect email format');
    //             valid = false;
    //             e.preventDefault();
    //             return;
    //         }
    //         if (number.trim().length >= 6) {
    //             if (password.match(regex)) {
    //                 setError('Incorrect number format');
    //                 valid = false;
    //                 e.preventDefault();
    //                 return;
    //             }
    //         } else {
    //             setError('Number is too short')
    //         }
    //         if (password.trim().length >= 6) {
    //             if (!password.match(/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/)) {
    //                 setError('Incorrect password format');
    //                 valid = false;
    //                 e.preventDefault();
    //                 return;
    //             }
    //         } else {
    //             setError('Password is too short')
    //         }
    //
    //     }
    //
    // }

    return (
        <div>
            <Button variant="primary" onClick={handleShow}>Registration</Button>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Registration</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form noValidate validated={validated} onSubmit={handleSubmit} style={{fontSize: "17px"}}>
                        <Form.Group className="mb-3" controlId="firstName">
                            <Form.Label>First name</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="Enter first name"
                            />
                            <Form.Control.Feedback>good job</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="surname">
                            <Form.Label>Surname</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="Enter surname"
                            />
                            <Form.Control.Feedback></Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="email">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control
                                type="email"
                                placeholder="Enter email"
                                required
                            />
                            <Form.Control.Feedback></Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="number">
                            <Form.Label>Phone number</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter phone number"
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="dateOfBirth">
                            <Form.Label>Date of birth</Form.Label>
                            <Form.Control
                                type="date"
                                placeholder="Enter date of birth"
                                required
                            />
                            <Form.Control.Feedback>good job</Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group>
                            <div className="form-check">
                                <input className="form-check-input" type="radio" name="flexRadioDefault"
                                       id="flexRadioDefault1"></input>
                                <label className="form-check-label" htmlFor="flexRadioDefault1">
                                    Copywriter
                                </label>
                            </div>
                            <div className="form-check">
                                <input className="form-check-input" type="radio" name="flexRadioDefault"
                                       id="flexRadioDefault2"></input>
                                <label className="form-check-label" htmlFor="flexRadioDefault2">
                                    Client
                                </label>
                            </div>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="password">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Enter password"
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="passwordSubmit">
                            <Form.Label>Submit password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Submit password"
                                required
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" type="submit">Register</Button>
                </Modal.Footer>
            </Modal>
        </div>
    )

}