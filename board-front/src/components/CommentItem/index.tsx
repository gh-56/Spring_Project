import React from 'react';
import './style.css';
import { CommentListItem } from 'types/interface';
import defaultProfileImage from 'assets/image/default-profile-image.png';

interface Props{
    commentListItem: CommentListItem;
}

//          component: 댓글 컴포넌트
export default function CommentItem({commentListItem}: Props) {
    //         properties          //
    const {nickname, profileImage, writeDateTime, content} = commentListItem;

    //         function          //
    //         event handler          //

  //          render: 댓글 컴포넌트 렌더링         //
  return (
    <div className='comment-list-item'>
        <div className="comment-list-item-top">
            <div className="comment-list-item-profile-box">
                <div className="comment-list-item-profile-image" style={{backgroundImage: `url(${profileImage ? profileImage : defaultProfileImage})`}}></div>
            </div>
            <div className="comment-list-item-profile-nickname">{nickname}</div>
            <div className="comment-list-item-profile-divider">{'\|'}</div>
            <div className="comment-list-item-profile-time">{writeDateTime}</div>
        </div>
        <div className="comment-list-item-main">
            <div className="comment-list-item-content">{content}</div>
        </div>

    </div>
  )
}
