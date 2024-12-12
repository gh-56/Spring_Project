import React from 'react';
import './App.css';
import BoardItem from 'components/BoardItem';
import { latestBoardListMock, top3BoardListMock, commentListMock, favoriteListMock } from 'mocks';
import Top3Item from 'components/Top3Item';
import CommentItem from 'components/CommentItem';
import FavoriteItem from 'components/FavoriteItem';

function App() {
  return (
    <>
      <div style={{display: "flex", columnGap: '30px', rowGap: '20px'}}>
        {favoriteListMock.map(favoriteItem => <FavoriteItem favoriteListItem={favoriteItem}/>)}
      </div>
    </>
  );
}

export default App;
