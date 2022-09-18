import { User } from "./user"
import { CommentReaction } from "./comment-reaction"

export interface Comment {
  author: User,
  content: string,
  reactions: Array<CommentReaction>,
  pinned: boolean
}

