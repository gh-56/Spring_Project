import React from 'react'
import './style.css';
import defaultProfileImage from 'assets/image/default-profile-image.png';
import { useNavigate } from 'react-router-dom';
import { FavoriteListItem } from 'types/interface';

interface Props{
    favoriteListItem: FavoriteListItem;
}

//          component: 좋아요 컴포넌트          //
export default function FavoriteItem({favoriteListItem}: Props) {
    //          properties          //
    const {nickname, profileImage} = favoriteListItem;

  //          render: 좋아요 컴포넌트 렌더링          //
  return (
    <div className='favorite-list-item'>
        <div className="favorite-list-item-profile-box">
            <div className="favorite-list-item-profile-image" style={{backgroundImage: `url(${profileImage ? profileImage : defaultProfileImage})`}}></div>
        </div>
        <div className="favorite-list-item-nickname">{nickname}</div>
    </div>
  )
}
