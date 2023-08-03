import React, { useState, useEffect } from 'react';

function ProfileUpload({ handleChange, profilePic }) {


  const imageMimeType = /(png|jpg|jpeg)/i;
  const [file, setFile] = useState(null);

  const convertImageToBase64 = (imageFile) => {
    if (imageFile) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onloadend = () => {
          resolve(reader.result);
        };
        reader.onerror = reject;
        reader.readAsDataURL(imageFile);
      });
    }
  };

  const handleBase64Conversion = async () => {
    if (file) {
      try {
        const base64Image = await convertImageToBase64(file);
        handleChange({
          target: {
            name: 'profilePic',
            value: base64Image
          }
        });
      } catch (error) {
        console.error('Error converting image to Base64:', error);
      }
    }
  };

  useEffect(() => {
    handleBase64Conversion();
  }, [file]);

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (!file) {
      handleChange({
        target: {
          name: 'profilePic',
          value: ''
        }
      });
      return;
    }
    if (!file.type.match(imageMimeType)) {
      alert("Image mime type is not valid");
      return;
    }
    setFile(file);
  };

  return (
    <div className="input-box-registerScreen">
      <div className="image-upload-container-registerScreen">
        <div className="choose-image-text-registerScreen">Profile Picture:</div>
        <div className="file-input-container-registerScreen">
          <input
            type="file"
            name="img"
            id="profile-pic-registerScreen"
            accept=".png, .jpg, .jpeg"
            onChange={handleImageChange}
            required
          />
          <img src={profilePic} alt="preview" />
        </div>
      </div>
    </div>
  );
}

export default ProfileUpload;
