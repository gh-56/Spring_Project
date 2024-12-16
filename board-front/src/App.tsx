import React, { useState } from 'react';
import './App.css';
import BoardItem from 'components/BoardItem';
import { latestBoardListMock, top3BoardListMock, commentListMock, favoriteListMock } from 'mocks';
import Top3Item from 'components/Top3Item';
import CommentItem from 'components/CommentItem';
import FavoriteItem from 'components/FavoriteItem';
import InputBox from 'components/InputBox';
import Footer from 'layouts/Footer';
import { Route, Routes } from 'react-router-dom';
import Main from 'Views/Main';
import Authentication from 'Views/Authentication';
import Search from 'Views/Search';
import User from 'Views/User';
import BoardDetail from 'Views/Board/Detail';
import BoardWrite from 'Views/Board/Write';
import BoardUpdate from 'Views/Board/Update';
import Container from 'layouts/Container';
import { AUTH_PATH, BOARD_DETAIL_PATH, BOARD_PATH, BOARD_UPDATE_PATH, BOARD_WRITE_PATH, MAIN_PATH, SEARCH_PATH, USER_PATH } from 'constant';


//          component: Application 컴포넌트          //
function App() {

  //          render: Application 컴포넌트 렌더링          //
  // description: 메인화면: '/' - Main //
  // description: 로그인 + 회원가입 화면: '/auth' - Authentication //
  // description: 검색 화면: '/search/:searchWord' - Search //
  // description: 유저 페이지: '/user/:userEmail' - User //
  // description: 게시물 상세보기: '/board/detail/:boardNumber' - BoardDetail //
  // description: 게시물 작성하기: '/board/write' - BoardWrite //
  // description: 게시물 수정하기: '/board/update/:boardNumber' - BoardUpdate //
  return (
    <Routes>
      <Route element={<Container />}>
        <Route path={MAIN_PATH()} element={<Main />} />
        <Route path={AUTH_PATH()} element={<Authentication />} />
        <Route path={SEARCH_PATH(':searchWord')} element={<Search />} />
        <Route path={USER_PATH(':userEmail')} element={<User />} />
        <Route path={BOARD_PATH()}>
          <Route path={BOARD_WRITE_PATH()} element={<BoardWrite />} />
          <Route path={BOARD_DETAIL_PATH(':boardNumber')} element={<BoardDetail />} />
          <Route path={BOARD_UPDATE_PATH(':boardNumber')} element={<BoardUpdate />} />
        </Route>
        <Route path="*" element={<h1>404 Not Found</h1>} />
      </Route>
    </Routes>
  );
}

export default App;
