import React from 'react';
import './App.css';
import BoardItem from 'components/BoardItem';
import { latestBoardListMock, top3BoardListMock, commentListMock } from 'mocks';
import Top3Item from 'components/Top3Item';
import CommentItem from 'components/CommentItem';

function App() {
  return (
    <>
      <div style={{padding: '0 20px', display: 'flex', flexDirection: 'column', gap: '30px'}}>
        {commentListMock.map(commentListItem => <CommentItem commentListItem={commentListItem}/>)}

      </div>
    </>
  );
}

export default App;
