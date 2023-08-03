import React, { useState, useEffect } from 'react';
import { Toast } from 'react-bootstrap';
import './toastify.css';

const CustomToast = ({ message, onClose }) => {
  const [show, setShow] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShow(false);
    }, 5000); // Auto-hide the toast after 5 seconds

    return () => clearTimeout(timer);
  }, []);

  const handleClose = () => {
    setShow(false);
    if (onClose) {
      onClose();
    }
  };

  return (
    <Toast
      show={show}
      onClose={handleClose}
      delay={5000}
      autohide
      className="custom-toast"
    >
      <Toast.Header closeButton={false} className="custom-toast-header">
        <strong className="me-auto"></strong>
      </Toast.Header>
      <Toast.Body>{message}</Toast.Body>
    </Toast>
  );
};


export default CustomToast;
