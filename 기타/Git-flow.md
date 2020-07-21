# Git-flow

[참고1](https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html) - 우아한형제들 기술블로그

[참고2](https://moood.dev/git/git-command/) - 개인블로그

[참고3](https://k39335.tistory.com/82) - 개인블로그

### Git-flow 가 무엇인가?

- 깃플로우 전략은 소프트웨어의 소스코드를 관리하고 출시하기 위한 '브랜치 관리 전략(branch management strategy)' 중 하나이다
- 5가지 브랜치로 나뉘며 (master, develop, feature, realease, hotfix)가 있다.

### Git Repository 구성

- Upstream Remote Repository

  - 개발자들이 공유하는 저장소로 최신 소스코드가 저장되어 있는 원격 저장소

- - Origin Remote Repository

  - Upstream Repository를 Fork한 원격 개인 저장소

- Local Repository

  - 내 컴퓨터에 저장되어 있는 개인 저장소

### 주요 Commands

- git clone [사용자명@도메인:/원격/저장소/경로]

  로컬(사용자pc)에 실제 서버(origin, 원격지)의 프로젝트 소스 파일들이 없는 경우, 실제 서버에 저장되어 있는 소스들을 받아올 때 사용됩니다.

- git flow init

  git의 확장인 git-flow 명령어들을 지속적으로 사용하기 위해 사용하는 명령어입니다. 저장소들은 초기화하는 과정인데, 대체로 git clone 후에 최초 1회만 사용됩니다.

  - 기존 git 저장소 내에서 초기화하는 것으로 Git-flow의 사용을 시작합니다.

  

- git checkout [저장소(branch)이름]

  다른 저장소에 접근하고 싶을 때 사용하는 명령어입니다.

  

- git branch

  기본적으로는 로컬(사용자 pc)의 저장소(branch)들을 확인할 때 사용합니다. 최초 'git clone'을 받고나서는 master만 존재하며, 'git flow init' 명령어를 사용한 후에는 master,develop가 존재하게 됩니다. 해당 명령어를 통해 사용자가 현재 어떤 저장소(branch)에 접근 중인지도 확인이 가능합니다.

  

- git add [파일경로/파일명 혹은 파일명]

  현재 사용자의 PC에서 작업한 소스들을 실제 서버(origin, 원격지)에 업로드하기 위해서 사용합니다. 하지만, 실제로 업로드 하는 것이 아니라 임시적으로 어떤 파일들을 업로드 할 것인지 정하는 단계입니다.

  

- git commit -m '메세지'

  git add를 통해 임시적으로 어떤 파일을 올릴 것인지 정한 후에 사용하는 명령어입니다. 현재 사용자의 PC(로컬)에 추가/삭제/변경된 소스들을 임시저장소(로컬저장소)에 업로드한다고 보시면 됩니다. commit을 사용하면 로컬에서도 이력(history, log)을 관리 할 수 있습니다.

  

- git pull origin [저장소(branch)이름]

  다른 개발자분이 작업한 내용(push한 소스들)을 가져오기 위해 사용합니다.

   

- git push origin [저장소(branch)이름]

  커밋한 내용들을 실제 서버(origin, 원격지)에 업로드, 적용하기 위해 사용합니다. push 하기 전에 pull 명령어를 통해 로컬 데이터를 최신 데이터로 갱신 후에 사용합니다.

  

- git flow hotfix start [VERSION]

  새로운 핫픽스 저장소(branch)를 만드는 git flow 명령어입니다. 해당 명령어를 실행하기 전에 git pull을 통해 최신 데이터를 받아 온 후에 실행하는게 좋습니다.

  

- git flow hotfix finish [VERSION]

  핫픽스 저장소에서 추가/삭제/변경된 내용들을 master 및 develop 브랜치에서 적용시키기 위해 사용하는 명령어입니다.

  

- git flow feature start [저장소(branch)이름]

  새 기능의 개발은 develop branch에서 시작합니다. develop branch에서 기반한 새 branch를 만들고, 그 branch로 전환합니다.

  

- git flow feature finish[저장소(branch)이름]

  새 기능의 개발이 완료되면 해당 명령어를 수행합니다. feature branch를 develop branch에 merge하고, 해당 feature branch를 삭제하고 develop branch로 돌아간다.

  

- git flow feature publish [저장소(branch)이름]

  다른 팀원들과 공동으로 개발 중이라면, remote에 게시하여 다른 사람들이 사용할 수 있게 한다.

  

- git flow feature pull origin [저장소(branch)이름]

  다른 사람이 게시한 기능을 가져온다.

  

- git flow release start <VERSION> or git flow release publish <VERSION>

  릴리즈 시작

  

- git flow release finish <VERSION>

  릴리즈 완료